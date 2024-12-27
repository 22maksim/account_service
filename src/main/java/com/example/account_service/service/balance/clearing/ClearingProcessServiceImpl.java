package com.example.account_service.service.balance.clearing;

import com.example.account_service.dto.balance.BalanceTransactionRequestDto;
import com.example.account_service.exeption.DataBalanceException;
import com.example.account_service.model.Balance;
import com.example.account_service.model.enums.AccountStatus;
import com.example.account_service.model.enums.TypeTransactionBalance;
import com.example.account_service.repository.balance.BalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClearingProcessServiceImpl implements ClearingProcessService {
    private final BalanceRepository balanceRepository;

    @Override
    @Transactional
    public Balance processing(BalanceTransactionRequestDto transactionRequest, Long balanceId) {
        Balance balance = balanceRepository.findBalanceById(balanceId);
        validated(balance, transactionRequest, balanceId);
        TypeTransactionBalance type = transactionRequest.getTypeTransactionBalance();
        return switch (type) {
            case CREDIT -> balanceCreditAuthorization(balance, transactionRequest);
            case FUNDS_DEPOSIT -> balanceFundsDeposit(balance, transactionRequest);
            case FUNDS_DEBITING -> balanceFundsDebiting(balance, transactionRequest);
            //сначала делал через Enum с лямбдами, но читаемость кода сложная, поэтому switch
        };
    }

    private Balance balanceFundsDeposit(Balance balance, BalanceTransactionRequestDto transactionRequest) {
        long newCurrentBalance = balance.getCurrentBalance() + transactionRequest.getDepositAmount();
        balance.setCurrentBalance(newCurrentBalance);
        return balance;
    }

    private Balance balanceFundsDebiting(Balance balance, BalanceTransactionRequestDto transactionRequest) {
        if (transactionRequest.getTypeTransactionBalance().equals(TypeTransactionBalance.FUNDS_DEBITING)) {
            long newCurrentBalance = balance.getCurrentBalance() - transactionRequest.getDebitAmount();
            if (newCurrentBalance < balance.getAuthorizationBalance()) {
                log.info("The balance cannot be less than the authorization amount. Transaction: {}",
                        transactionRequest.getNumberOperation());
                throw new DataBalanceException("The balance cannot be less than the authorization amount. Transaction: "
                        + transactionRequest.getNumberOperation());
            }
            balance.setCurrentBalance(newCurrentBalance);
        }
        return balance;
    }

    private Balance balanceCreditAuthorization(Balance balance, BalanceTransactionRequestDto transactionRequest) {
        if (transactionRequest.getTypeTransactionBalance().equals(TypeTransactionBalance.CREDIT)) {
            balance.setAuthorizationBalance(transactionRequest.getCreditAmount());
        }
        return balance;
    }

    private void validated(Balance balance, BalanceTransactionRequestDto transactionRequest, Long balanceId) {
        if (balance == null) {
            log.info("Balance not found. Transaction: {}", transactionRequest.getNumberOperation());
            throw new DataBalanceException("Balance not found. Id:" + balanceId);
        }
        if (balance.getAccount().getStatus().equals(AccountStatus.BLOCKED)) {
            log.info("Account status is BLOCKED. Transaction: {}, cannot be executed",
                    transactionRequest.getNumberOperation());
            throw new DataBalanceException(String.format("Account status is CLOSED. Transaction: %s cannot be executed",
                    transactionRequest.getNumberOperation()));
        }
        if (balance.getAccount().getStatus().equals(AccountStatus.CLOSED)) {
            log.info("Account status is CLOSED. Transaction: {} cannot be executed",
                    transactionRequest.getNumberOperation());
            throw new DataBalanceException(String.format("Account status is CLOSED. Transaction: %s cannot be executed",
                    transactionRequest.getNumberOperation()));
        }
        if (!(balance.getCurrentBalance() > transactionRequest.getCreditAmount())
                && !(balance.getCurrentBalance() > transactionRequest.getDebitAmount())) {
            log.info("There is not enough money on the balance. Transaction: {}",
                    transactionRequest.getNumberOperation());
            throw new DataBalanceException("There is not enough money on the balanceTransaction: "
                    + transactionRequest.getNumberOperation());
        }
    }
}
