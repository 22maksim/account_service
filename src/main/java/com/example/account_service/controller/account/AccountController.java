package com.example.account_service.controller.account;

import com.example.account_service.dto.AccountRequestDto;
import com.example.account_service.dto.AccountResponseDto;
import com.example.account_service.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("api/v1/account/")
@RequiredArgsConstructor
@Tag(name = "Recommendation API for Account Service", description = "API for managing account service")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "get account by id", description = "Return the AccountResponseDto found by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The AccountResponseDto found and returning"),
            @ApiResponse(responseCode = "404", description = "The AccountResponseDto not found by id")
    })
    @GetMapping("{id}")
    public AccountResponseDto get(
            @PathVariable @Positive @NotNull @Parameter(description = "This is id from search Account by id") Long id) {
        return accountService.get(id);
    }

    @PutMapping
    public AccountResponseDto open(@RequestBody @Valid @NotNull AccountRequestDto accountRequestDto) {
        return accountService.open(accountRequestDto);
    }

    @PostMapping("{id}")
    public AccountResponseDto block(@PathVariable @NotNull @Positive Long id) {
        return accountService.block(id);
    }

    @PostMapping("{id}/close")
    public AccountResponseDto close(@PathVariable @NotNull @Positive Long id,
                                    @RequestBody @NotNull @Valid AccountRequestDto accountRequestDto) {
        return accountService.close(id, accountRequestDto);
    }
}
