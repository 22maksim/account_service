package com.example.account_service.model.cashback.tariff;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tariff_merchant_percents")
public class TariffMerchantPercents {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "tariff_cashback_id", nullable = false)
  private TariffCashback tariffCashback;

  @ManyToOne
  @JoinColumn(name = "merchant_percents_id")
  private MerchantPercent merchantPercent;
}