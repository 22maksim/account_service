package com.example.account_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


import java.util.UUID;

public class account {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID id;
}
