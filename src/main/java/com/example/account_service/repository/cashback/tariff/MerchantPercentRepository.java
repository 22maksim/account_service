package com.example.account_service.repository.cashback.tariff;

import com.example.account_service.model.cashback.tariff.MerchantPercent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface MerchantPercentRepository extends JpaRepository<MerchantPercent, Long> {
}