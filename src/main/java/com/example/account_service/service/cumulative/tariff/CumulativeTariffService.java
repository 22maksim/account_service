package com.example.account_service.service.cumulative.tariff;

import com.example.account_service.model.dto.cumulative.tariff.CumulativeTariffRequestDto;
import com.example.account_service.model.dto.cumulative.tariff.CumulativeTariffResponseDto;

import java.util.List;

public interface CumulativeTariffService {
    CumulativeTariffResponseDto createCumulativeTariff(CumulativeTariffRequestDto requestDto);

    CumulativeTariffResponseDto updateCumulativeTariff(CumulativeTariffRequestDto requestDto);

    List<CumulativeTariffResponseDto> getAllCumulativeTariffs();
}
