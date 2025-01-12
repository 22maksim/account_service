package com.example.account_service.controller.cashback;

import com.example.account_service.model.dto.cashback.ResponseTariffCashbackDto;
import com.example.account_service.model.dto.cashback.RequestTariffCashbackDto;

import com.example.account_service.service.cashback.TariffCashbackService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cashback")
@RequiredArgsConstructor
public class CashbackTariffController {
    private final TariffCashbackService tariffCashbackServiceImpl;

    @PostMapping
    public ResponseTariffCashbackDto createdCashback(RequestTariffCashbackDto requestTariffCashbackDto) {
        return tariffCashbackServiceImpl.createdCashback(requestTariffCashbackDto);
    }

    @GetMapping("/{id}")
    public ResponseTariffCashbackDto getCashbackTariff(@PathVariable @NotNull @Positive Long id) {
        return tariffCashbackServiceImpl.getCashbackTariff(id);
    }
}
