package com.example.account_service.controller.cumulative.tariff;

import com.example.account_service.model.dto.cumulative.tariff.CumulativeTariffRequestDto;
import com.example.account_service.model.dto.cumulative.tariff.CumulativeTariffResponseDto;
import com.example.account_service.service.cumulative.tariff.CumulativeTariffService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("api/v1/cumulative-tariff/")
@RequiredArgsConstructor
public class CumulativeTariffController {
    private final CumulativeTariffService cumulativeTariffServiceImpl;

    @PostMapping
    public CumulativeTariffResponseDto createCumulativeTariff(@RequestBody @NotNull @Valid CumulativeTariffRequestDto requestDto) {
        return
    }
}
