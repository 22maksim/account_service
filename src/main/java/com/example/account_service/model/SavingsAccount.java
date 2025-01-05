package com.example.account_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "savings_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingsAccount {
    @Id
    private String id;

    @Column(name = "tariff_history")
    private String tariffHistory;

    @Column(name = "update_percent")
    private Timestamp updatePercent;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @Version
    private int version;
}
