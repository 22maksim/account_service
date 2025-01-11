package com.example.account_service.model.cashback.tariff;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operation_type_percents")
public class OperationTypePercent {
    @Id
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "operation_type_id", nullable = false)
    private TypeOperation typeOperation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "percents_id", nullable = false)
    private Percents percents;
}
