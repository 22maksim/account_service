package com.example.account_service.model.cashback.tariff;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name = "tariff_cashback")
@NoArgsConstructor
@AllArgsConstructor
public class TariffCashback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant created;

    @BatchSize(size = 5)
    @OneToMany(mappedBy = "tariffCashback", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TariffMerchantPercents> tariffMerchantPercents = new ArrayList<>();

    @BatchSize(size = 5)
    @OneToMany(mappedBy = "tariffCashback", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TariffOperationTypePercents> tariffOperationTypePercents = new ArrayList<>();
}
