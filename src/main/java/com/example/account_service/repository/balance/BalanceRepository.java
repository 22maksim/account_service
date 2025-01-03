package com.example.account_service.repository.balance;

import com.example.account_service.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Balance findBalanceById(Long id);
}
