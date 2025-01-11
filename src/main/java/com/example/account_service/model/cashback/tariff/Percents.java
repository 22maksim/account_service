package com.example.account_service.model.cashback.tariff;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "percents")
public class Percents {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "percent", nullable = false)
  private BigDecimal percent;

  @Column(name = "name", nullable = false)
  private String name;
}