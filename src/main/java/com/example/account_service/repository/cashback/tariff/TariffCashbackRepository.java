package com.example.account_service.repository.cashback.tariff;

import com.example.account_service.model.cashback.tariff.TariffCashback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TariffCashbackRepository extends JpaRepository<TariffCashback, Long> {
}