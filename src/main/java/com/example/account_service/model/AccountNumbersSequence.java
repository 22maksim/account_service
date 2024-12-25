package com.example.account_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "account_numbers_sequence")
public class AccountNumbersSequence {
    @Id
    @Column(name = "type", nullable = false, length = 15)
    private String type;

    @Column(name = "counter", nullable = false)
    private long counter;

    @Version
    private int version;
}
