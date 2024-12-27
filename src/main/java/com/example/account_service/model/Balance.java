package com.example.account_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Builder
@Table(name = "balance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "id", cascade = CascadeType.ALL)
    private Account account;

    @Column(name = "authorization_balance", nullable = false)
    private long authorizationBalance;

    @Column(name = "current_balance")
    private long currentBalance;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "update_at")
    private Timestamp updatedAt;

    @Version
    private int version;
}
