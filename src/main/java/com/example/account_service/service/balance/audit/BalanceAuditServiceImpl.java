package com.example.account_service.service.balance.audit;

import com.example.account_service.exeption.DataAccountException;
import com.example.account_service.model.Account;
import com.example.account_service.model.Balance;
import com.example.account_service.model.BalanceAudit;
import com.example.account_service.model.dto.balance.BalanceResponseDto;
import com.example.account_service.model.dto.balance.BalanceTransactionRequestDto;
import com.example.account_service.model.enums.TypeTransactionBalance;
import com.example.account_service.model.properties.BoxProperties;
import com.example.account_service.repository.account.AccountRepository;
import com.example.account_service.repository.balance.audit.BalanceAuditRepository;
import com.example.account_service.service.balance.BalanceService;
import com.example.account_service.service.balance.audit.cashback_accrual.CashbackAccrualProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceAuditServiceImpl implements BalanceAuditService {
    private final BalanceAuditRepository balanceAuditRepository;
    private final BalanceService balanceServiceImpl;
    private final AccountRepository accountRepository;
    private final CashbackAccrualProcess cashbackAccrualProcess;
    private final BoxProperties boxProperties;

    @Transactional
    @Override
    public void saveBalanceAudit(BalanceAudit audit) {
        Account account = audit.getAccount();
        if (account == null) {
            log.error("Account not found");
            throw new DataAccountException("BalanceAudit... Account not found");
        }
        balanceAuditRepository.save(audit);
    }

    @Transactional
    @Override
    public void updateBalanceAudit(BalanceResponseDto responseDto, BalanceTransactionRequestDto transactionRequestDto) {
        Account account = accountRepository.findAccountById(responseDto.getAccount_id());
        BalanceAudit audit = BalanceAudit.builder()
                .account(account)
                .type(account.getType())
                .authorizationAmount(responseDto.getAuthorizationBalance())
                .actual_amount(responseDto.getCurrentBalance())
                .transactionChanged(transactionRequestDto.getNumberOperation())
                .typeTransactionBalance(transactionRequestDto.getTypeTransactionBalance())
                .amountOperation(transactionRequestDto.getDebitAmount())
                .build();
        if (transactionRequestDto.getMerchantId() != null) {
            audit.setMerchantPercentId(transactionRequestDto.getMerchantId());
        }
        if (transactionRequestDto.getOperationTypeId() != null) {
            audit.setOperationTypePercentId(transactionRequestDto.getOperationTypeId());
        }
        balanceAuditRepository.save(audit);
    }

    @Override
    @Transactional
    public void cashbackAccrual() {
        Timestamp timestampOneMonthAgo = Timestamp.valueOf(LocalDateTime.now().minusMonths(1));
        int page = 0;
        Pageable pageable = PageRequest.of(page, boxProperties.size(), Sort.by("createdAt").descending());
        Page<BalanceAudit> pageBalance;

        List<Balance> allUpdatedBalances = new ArrayList<>();

        do {
            pageBalance = balanceAuditRepository.findAllByTypeTransactionBalanceAndCreatedAt(
                    TypeTransactionBalance.FUNDS_DEBITING, timestampOneMonthAgo, pageable);

            List<CompletableFuture<Balance>> futures = pageBalance.get()
                    .collect(Collectors.groupingBy(BalanceAudit::getAccount))
                    .entrySet()
                    .stream()
                    .map(entry -> cashbackAccrualProcess.processing(entry.getKey(), entry.getValue()))
                    .toList();

            List<Balance> updatedBalances = futures.stream()
                    .map(CompletableFuture::join)
                    .toList();

            allUpdatedBalances.addAll(updatedBalances);
            page++;
        } while (pageBalance.hasNext());

        balanceServiceImpl.saveAllBalances(allUpdatedBalances);
    }

}
