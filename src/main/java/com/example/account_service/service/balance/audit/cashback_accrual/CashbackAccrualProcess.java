package com.example.account_service.service.balance.audit.cashback_accrual;

import com.example.account_service.model.Account;
import com.example.account_service.model.Balance;
import com.example.account_service.model.BalanceAudit;
import com.example.account_service.model.cashback.tariff.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class CashbackAccrualProcess {

    @Async("forkJoinPool")
    public CompletableFuture<Balance> processing(Account account, List<BalanceAudit> balancesAudit) {
        TariffCashback tariffCashback = account.getTariffCashback();

        List<MerchantPercent> merchantPercentList = getValidMerchantPercents(balancesAudit, tariffCashback);
        List<OperationTypePercent> operationTypePercentsList = getValidOperationTypePercents(balancesAudit, tariffCashback);

        Long resultSum = balancesAudit.parallelStream()
                .map(balanceAudit -> calculateSumForAudit(balanceAudit, merchantPercentList, operationTypePercentsList))
                .reduce(0L, Long::sum);

        Balance balance = account.getBalance();
        balance.setCurrentBalance(balance.getCurrentBalance() + resultSum);

        return CompletableFuture.completedFuture(balance);
    }

    private List<MerchantPercent> getValidMerchantPercents(List<BalanceAudit> balancesAudit, TariffCashback tariffCashback) {
        List<Long> validMerchantPercentsIds = balancesAudit.stream()
                .map(BalanceAudit::getMerchantPercentId)
                .filter(Objects::nonNull)
                .toList();

        return tariffCashback.getTariffMerchantPercents().stream()
                .map(TariffMerchantPercents::getMerchantPercent)
                .filter(merchantPercent -> validMerchantPercentsIds.contains(merchantPercent.getId()))
                .toList();
    }

    private List<OperationTypePercent> getValidOperationTypePercents(List<BalanceAudit> balancesAudit, TariffCashback tariffCashback) {
        List<Long> validOperationTypePercentsIds = balancesAudit.stream()
                .map(BalanceAudit::getOperationTypePercentId)
                .filter(Objects::nonNull)
                .toList();

        return tariffCashback.getTariffOperationTypePercents().stream()
                .map(TariffOperationTypePercents::getOperationTypePercent)
                .filter(operationTypePercent -> validOperationTypePercentsIds.contains(operationTypePercent.getId()))
                .toList();
    }

    private Long calculateSumForAudit(BalanceAudit balanceAudit, List<MerchantPercent> merchantPercentList,
                                      List<OperationTypePercent> operationTypePercentsList) {
        long sum = 0L;

        if (balanceAudit.getMerchantPercentId() != null) {
            MerchantPercent merchantPercent = merchantPercentList.stream()
                    .filter(mp -> mp.getId().equals(balanceAudit.getMerchantPercentId()))
                    .findFirst()
                    .orElse(null);
            assert merchantPercent != null;
            sum += calculateAmount(balanceAudit.getAmountOperation(), merchantPercent.getPercents());
        }

        if (balanceAudit.getOperationTypePercentId() != null) {
            OperationTypePercent operationTypePercent = operationTypePercentsList.stream()
                    .filter(op -> op.getId().equals(balanceAudit.getOperationTypePercentId()))
                    .findFirst()
                    .orElse(null);
            assert operationTypePercent != null;
            sum += calculateAmount(balanceAudit.getAmountOperation(), operationTypePercent.getPercents());
        }

        return sum;
    }

    private Long calculateAmount(Long amount, Percents percents) {
        assert percents != null;
        return BigDecimal.valueOf(amount)
                .multiply(percents.getPercent())
                .longValue();
    }
}
