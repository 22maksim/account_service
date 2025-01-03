package com.example.account_service.service.dms;

import com.example.account_service.config.kafka.topic.KafkaTopics;
import com.example.account_service.exeption.DataAccountException;
import com.example.account_service.exeption.DataBalanceException;
import com.example.account_service.exeption.DataPendingException;
import com.example.account_service.mapper.pending.PendingMapper;
import com.example.account_service.model.Account;
import com.example.account_service.model.Balance;
import com.example.account_service.model.Pending;
import com.example.account_service.model.dto.MyMessageDto;
import com.example.account_service.model.dto.payment.PaymentResponseDto;
import com.example.account_service.model.enums.PaymentStatus;
import com.example.account_service.model.enums.TypePaymentRequest;
import com.example.account_service.repository.account.AccountRepository;
import com.example.account_service.repository.pending.PendingRepository;
import com.example.account_service.service.kafka.publisher.KafkaPublisherService;
import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PessimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DmsRequestsProcessing {
    private final PendingRepository pendingRepository;
    private final AccountRepository accountRepository;
    private final PendingMapper pendingMapper;
    private final KafkaPublisherService<PaymentResponseDto> kafkaPublisherService;
    private final KafkaTopics kafkaTopics;

    @Transactional
    public void startAuthorisation(List<Pending> pendings) {
        pendings.forEach(this::validateAndConfirmRequest);
        pendingRepository.saveAll(pendings);
    }

    @Async("workerAsync")
    @Transactional
    @Retryable(retryFor = {PessimisticLockException.class, OptimisticLockException.class, LockTimeoutException.class},
            backoff = @Backoff(delay = 1000, multiplier = 3))
    public void validateAndConfirmRequest(Pending pending) {
        if (!pending.getTypePaymentRequest().equals(TypePaymentRequest.AUTHORIZATION)) {
            log.error("Pending type payment request is incorrect. Must be AUTHORIZATION");
            throw new DataAccountException("Pending type payment request is incorrect.Must be AUTHORIZATION");
        }
        Account account = getAccount(pending);
        Balance balance = getBalance(account, pending);
        long amountAuthorisationSum = pending.getAmount() + balance.getAuthorizationBalance();
        if (balance.getCurrentBalance() > amountAuthorisationSum) {
            balance.setAuthorizationBalance(amountAuthorisationSum);
            pending.setPaymentStatus(PaymentStatus.SUCCESS);
            pending.setMessage("Payment successful");
            sendInKafka(pending, kafkaTopics.responseAuthorisationTopic());
        } else {
            log.error("Current balance is too low");
            pending.setMessage("Current balance is too low");
            pending.setPaymentStatus(PaymentStatus.CANCELLATION);
            sendInKafka(pending, kafkaTopics.responseCancelTopic());
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(retryFor = {PessimisticLockException.class, OptimisticLockException.class, LockTimeoutException.class},
            backoff = @Backoff(delay = 1000, multiplier = 3))
    public void startClearingCapture(Pending pending) {
        if (!pending.getTypePaymentRequest().equals(TypePaymentRequest.CAPTURE)) {
            log.error("Pending type payment request is incorrect. Must be CAPTURE");
            throw new DataPendingException("Pending type payment request is incorrect. Must be CAPTURE");
        }
        Account account = getAccount(pending);
        Balance balance = getBalance(account, pending);

        long amountAuthorisationResult = balance.getAuthorizationBalance() - pending.getAmount();
        long amountBalanceResult = balance.getCurrentBalance() - pending.getAmount();

        balance.setAuthorizationBalance(amountAuthorisationResult);
        balance.setCurrentBalance(amountBalanceResult);

        pending.setPaymentStatus(PaymentStatus.SUCCESS);
        pending.setMessage("Payment successful");

        pendingRepository.save(pending);
        sendInKafka(pending, kafkaTopics.responseClearingTopic());
    }

    @Transactional("kafkaTransactionManager")
    public void sendInKafka(Pending pending, String topic) {
        PaymentResponseDto responseDto = pendingMapper.pendingToResponseDto(pending);
        MyMessageDto<PaymentResponseDto> myMessageDto = MyMessageDto.<PaymentResponseDto>builder()
                .myEntity(responseDto).build();
        kafkaPublisherService.sendResponseMessage(myMessageDto, topic);
    }

    @Transactional
    public Account getAccount(Pending pending) {
        Account account = accountRepository.findAccountById(pending.getAccountId());
        if (account == null) {
            log.error("Account not found");
            pending.setPaymentStatus(PaymentStatus.CANCELLATION);
            throw new DataAccountException("Account not found");
        }
        if (!account.getCurrency().equals(pending.getCurrency())) {
            log.error("Current currency does not match");
            pending.setPaymentStatus(PaymentStatus.CANCELLATION);
            throw new DataAccountException("Current currency does not match");
        }
        return account;
    }

    @Transactional
    public Balance getBalance(Account account, Pending pending) {
        Balance balance = account.getBalance();
        if (balance == null) {
            log.error("Balance not found");
            pending.setPaymentStatus(PaymentStatus.CANCELLATION);
            throw new DataBalanceException("Balance not found");
        }
        return balance;
    }
}
