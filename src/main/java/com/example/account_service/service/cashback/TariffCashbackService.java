package com.example.account_service.service.cashback;

import com.example.account_service.model.cashback.tariff.TariffCashback;
import com.example.account_service.model.dto.cashback.RequestTariffCashbackDto;
import com.example.account_service.model.dto.cashback.ResponseTariffCashbackDto;

public interface TariffCashbackService {
    ResponseTariffCashbackDto createdCashback(RequestTariffCashbackDto requestTariffCashbackDto);

    ResponseTariffCashbackDto getCashbackTariff(Long id);

    TariffCashback getTariffCashbackEntity(Long id);
}
