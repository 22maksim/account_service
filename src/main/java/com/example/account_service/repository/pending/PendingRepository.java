package com.example.account_service.repository.pending;

import com.example.account_service.model.Pending;
import com.example.account_service.model.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PendingRepository extends JpaRepository<Pending, Long> {

    Page<Pending> findByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable);

    Optional<Pending> findByNumberTransactionAndAccountId(long numberTransaction, String accountId);
}
