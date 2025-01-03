package com.example.account_service.model;

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
@Table(name = "request")
public class Request {
    @Id
    private Long id;

    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_request")
    private TypePaymentRequest typeRequest;
}
