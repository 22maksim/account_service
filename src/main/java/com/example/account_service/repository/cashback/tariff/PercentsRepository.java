package com.example.account_service.repository.cashback.tariff;

import com.example.account_service.model.cashback.tariff.Percents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PercentsRepository extends JpaRepository<Percents, Long> {
}