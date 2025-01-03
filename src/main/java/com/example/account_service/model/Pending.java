package com.example.account_service.model;

import com.example.account_service.model.enums.Currency;
import com.example.account_service.model.enums.PaymentStatus;
import com.example.account_service.model.enums.TypePaymentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pending", uniqueConstraints = {@UniqueConstraint(columnNames = {"account_id", "number_transaction"})})
public class Pending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "account_id", nullable = false, length = 20)
    private String accountId;

    @Column(name = "number_transaction", nullable = false)
    private long numberTransaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_payment_request", nullable = false, length = 15)
    private TypePaymentRequest typePaymentRequest;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "message", length = 300)
    private String message;

    @Version
    @Column(name = "version")
    private int version;
}