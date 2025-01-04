package com.example.account_service.model;

import com.example.account_service.model.enums.AccountStatus;
import com.example.account_service.model.enums.Currency;
import com.example.account_service.model.enums.TariffType;
import com.example.account_service.model.enums.TypeNumber;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Table(name = "account")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private Owner owner;

    @OneToOne
    @JoinColumn(name = "balance_id", referencedColumnName = "id")
    private Balance balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeNumber type;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @Column(name = "close_at")
    private Timestamp closeAt;

    @Column(name = "tariff_type")
    private TariffType tariffType;

    @Version
    private int version;
}
