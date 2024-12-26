package com.example.account_service.model;

import com.example.account_service.model.enums.AccountStatus;
import com.example.account_service.model.enums.Currency;
import com.example.account_service.model.enums.TypeAccountNumber;
import jakarta.persistence.*;
import lombok.*;


import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Table(name = "account")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private Owner owner;

    @Column(name = "type", nullable = false)
    private TypeAccountNumber type;

    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updateAt;

    @Column(name = "close_at")
    private Timestamp closeAt;

    @Version
    private int version;
}
