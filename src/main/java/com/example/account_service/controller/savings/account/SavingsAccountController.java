package com.example.account_service.controller.savings.account;

import com.example.account_service.model.dto.savings.account.SavingsAccountRequestDto;
import com.example.account_service.model.dto.savings.account.SavingsAccountResponseDto;
import com.example.account_service.service.savings.account.SavingsAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/savings-account/")
@RequiredArgsConstructor
public class SavingsAccountController {
    private final SavingsAccountService savingsAccountServiceImpl;

    @PostMapping
    public SavingsAccountResponseDto createSavingsAccount(@RequestBody @NotNull @Valid SavingsAccountRequestDto savingsAccountRequestDto) {
        return savingsAccountServiceImpl.createSavingsAccount(savingsAccountRequestDto);
    }

    @GetMapping("{accountId}")
    public SavingsAccountResponseDto getSavingsAccount(@PathVariable @NotEmpty String accountId) {
        return savingsAccountServiceImpl.getSavingsAccount(accountId);
    }
}
