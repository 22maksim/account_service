package com.example.account_service.model;


import com.example.account_service.model.enums.TariffType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "cumulative_tariff")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CumulativeTariff {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, unique = true)
    private TariffType type;

    @Column(name = "history", nullable = false, length = 20)
    private String history;

    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updateAt;
}
