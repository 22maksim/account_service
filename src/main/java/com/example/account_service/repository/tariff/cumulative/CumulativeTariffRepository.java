package com.example.account_service.repository.tariff.cumulative;

import com.example.account_service.model.CumulativeTariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CumulativeTariffRepository extends JpaRepository<CumulativeTariff, String> {
}
