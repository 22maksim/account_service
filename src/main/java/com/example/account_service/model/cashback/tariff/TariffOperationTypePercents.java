package com.example.account_service.model.cashback.tariff;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tariff_operation_type_percents")
public class TariffOperationTypePercents {
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "tariff_cashback_id", nullable = false)
    private TariffCashback tariffCashback;

    @ManyToOne
    @JoinColumn(name = "operation_type_percents_id", nullable = false)
    private OperationTypePercent operationTypePercent;
}