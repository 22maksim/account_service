package com.example.account_service.repository.balance.audit;

import com.example.account_service.model.BalanceAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceAuditRepository extends JpaRepository<BalanceAudit, Long> {
}
