package com.example.account_service.controller.cumulative.tariff;

import com.example.account_service.model.dto.cumulative.tariff.CumulativeTariffRequestDto;
import com.example.account_service.model.dto.cumulative.tariff.CumulativeTariffResponseDto;
import com.example.account_service.service.account.cumulative.tariff.CumulativeTariffService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/cumulative-tariff/")
@RequiredArgsConstructor
public class CumulativeTariffController {
    private final CumulativeTariffService cumulativeTariffServiceImpl;

    @PostMapping
    public CumulativeTariffResponseDto createCumulativeTariff(
            @RequestBody @NotNull @Valid CumulativeTariffRequestDto requestDto) {
        return cumulativeTariffServiceImpl.createCumulativeTariff(requestDto);
    }

    @PostMapping("update")
    public CumulativeTariffResponseDto updateCumulativeTariffById(
            @RequestBody @NotNull @Valid CumulativeTariffRequestDto requestDto) {
        return cumulativeTariffServiceImpl.updateCumulativeTariff(requestDto);
    }

    @GetMapping
    public List<CumulativeTariffResponseDto> getAllCumulativeTariffs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return cumulativeTariffServiceImpl.getAllCumulativeTariffs(page, size);
    }
}
