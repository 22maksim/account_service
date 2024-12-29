package com.example.account_service.controller.balance;

import com.example.account_service.dto.balance.BalanceOpenRequestDto;
import com.example.account_service.dto.balance.BalanceResponseDto;
import com.example.account_service.dto.balance.BalanceTransactionRequestDto;
import com.example.account_service.service.balance.BalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@Tag(name = "Balance controller", description = "controller for working with balances")
@RequiredArgsConstructor
@RequestMapping("/api/v1/balance")
public class BalanceController {
    private final BalanceService balanceService;

    @GetMapping("{id}")
    public BalanceResponseDto getBalanceById(@PathVariable("id") @NotNull @Positive Long id) {
        return balanceService.getBalanceById(id);
    }

    @Operation(summary = "creating balance", description = "Open new balance from account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "balance created successfully"),
            @ApiResponse(responseCode = "400", description = "balance create failed")
    })
    @PostMapping
    public BalanceResponseDto createBalance(@NotNull @Valid @RequestBody BalanceOpenRequestDto balanceOpenRequestDto) {
        return balanceService.createBalance(balanceOpenRequestDto);
    }

    @Operation(summary = "update balance", description = "Balance change")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "balance updated successfully"),
            @ApiResponse(responseCode = "404", description = "balance update failed, because not found"),
            @ApiResponse(responseCode = "400", description = "balance update failed")
    })
    @PostMapping("{id}")
    public BalanceResponseDto updateBalance(@NotNull @Valid @RequestBody BalanceTransactionRequestDto transactionRequest,
                                            @NotNull @PathVariable("id") @NotNull @Positive Long id) {
        return balanceService.updateBalance(id, transactionRequest);
    }
}
