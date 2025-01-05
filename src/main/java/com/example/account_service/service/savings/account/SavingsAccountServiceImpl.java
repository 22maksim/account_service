package com.example.account_service.service.savings.account;

import com.example.account_service.exeption.DataAccountException;
import com.example.account_service.model.Account;
import com.example.account_service.model.CumulativeTariff;
import com.example.account_service.model.SavingsAccount;
import com.example.account_service.model.dto.savings.account.SavingsAccountRequestDto;
import com.example.account_service.model.dto.savings.account.SavingsAccountResponseDto;
import com.example.account_service.model.enums.TariffType;
import com.example.account_service.model.enums.TypeNumber;
import com.example.account_service.repository.account.AccountRepository;
import com.example.account_service.repository.savings.account.SavingsAccountRepository;
import com.example.account_service.repository.tariff.cumulative.CumulativeTariffRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavingsAccountServiceImpl implements SavingsAccountService {
    private final SavingsAccountRepository savingsAccountRepository;
    private final AccountRepository accountRepository;
    private final CumulativeTariffRepository cumulativeTariffRepository;
    private final ObjectMapper objectMapper;

    @Override
    public SavingsAccountResponseDto createSavingsAccount(SavingsAccountRequestDto savingsAccountRequestDto) {
        Account account = accountRepository.findAccountById(savingsAccountRequestDto.getAccountId());
        if (account == null || !account.getType().equals(TypeNumber.CUMULATIVE)) {
            log.error("Account with id {} not found", savingsAccountRequestDto.getAccountId());
            throw new DataAccountException(
                    String.format("Account with id %s not found", savingsAccountRequestDto.getAccountId()));
        }
        CumulativeTariff cumulativeTariff =
                cumulativeTariffRepository.findById(savingsAccountRequestDto.getTariffType().name())
                        .orElseThrow(() -> new DataAccountException("Cumulative Tariff Not Found"));
        int[] rates = objectMapper.convertValue(cumulativeTariff.getHistory(), int[].class);
        account.setTariffType(savingsAccountRequestDto.getTariffType());
        String[] tariffHistory = new String[]{savingsAccountRequestDto.getTariffType().name()};
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId(account.getId());
        try {
            savingsAccount.setTariffHistory(objectMapper.writeValueAsString(tariffHistory));
        } catch (JsonProcessingException e) {
            log.error("Mapping processing. Error: {}", e.getMessage());
            throw new DataAccountException("Mapping processing. Error: " + e.getMessage());
        }
        savingsAccount = savingsAccountRepository.save(savingsAccount);
        return SavingsAccountResponseDto.builder()
                .id(savingsAccount.getId())
                .rate(rates[rates.length - 1])
                .tariffType(account.getTariffType())
                .build();
    }

    @Override
    public SavingsAccountResponseDto getSavingsAccount(String accountId) {
        SavingsAccount savingsAccount = savingsAccountRepository.findById(accountId)
                .orElseThrow(() -> new DataAccountException("Saving Account Not Found"));
        String[] tariffHistory = objectMapper.convertValue(savingsAccount.getTariffHistory(), String[].class);
        CumulativeTariff cumulativeTariff = cumulativeTariffRepository.findById(tariffHistory[tariffHistory.length - 1])
                .orElseThrow(() -> new DataAccountException("Cumulative Tariff Not Found"));
        int[] rates = objectMapper.convertValue(cumulativeTariff.getHistory(), int[].class);
        return SavingsAccountResponseDto.builder()
                .id(savingsAccount.getId())
                .rate(rates[rates.length - 1])
                .tariffType(TariffType.valueOf(tariffHistory[tariffHistory.length - 1]))
                .build();
    }
}
