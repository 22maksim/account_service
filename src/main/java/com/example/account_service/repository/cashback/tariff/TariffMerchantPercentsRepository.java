package com.example.account_service.repository.cashback.tariff;

import com.example.account_service.model.cashback.tariff.TariffMerchantPercents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TariffMerchantPercentsRepository extends JpaRepository<TariffMerchantPercents, Long> {
}