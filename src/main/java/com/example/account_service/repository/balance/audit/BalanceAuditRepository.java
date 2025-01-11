package com.example.account_service.repository.balance.audit;

import com.example.account_service.model.BalanceAudit;
import com.example.account_service.model.enums.TypeTransactionBalance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;

public interface BalanceAuditRepository extends JpaRepository<BalanceAudit, Long> {

    @Query(value = """
        SELECT ba FROM BalanceAudit ba
        WHERE ba.typeTransactionBalance = :typeTransactionBalance
        AND ba.createdAt >= :dateChange
""")
    Page<BalanceAudit> findAllByTypeTransactionBalanceAndCreatedAt(TypeTransactionBalance typeTransactionBalance, Timestamp timeChange, Pageable pageable);
}
