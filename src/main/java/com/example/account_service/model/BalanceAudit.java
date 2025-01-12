package com.example.account_service.model;

import com.example.account_service.model.enums.TypeNumber;
import com.example.account_service.model.enums.TypeTransactionBalance;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@Table(name = "balance_audit")
@NoArgsConstructor
@AllArgsConstructor
public class BalanceAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "type", length = 15)
    private TypeNumber type;

    @Column(name = "authorization_amount")
    private long authorizationAmount;

    @Column(name = "actual_amount")
    private long actual_amount;

    @Column(name = "transaction_changed")
    private long transactionChanged;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "amount_operation")
    private Long amountOperation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_transactional_balance")
    private TypeTransactionBalance typeTransactionBalance;

    @Column(name = "operation_type_percent_id")
    private Long operationTypePercentId;

    @Column(name = "merchant_percent_id")
    private Long merchantPercentId;
}
