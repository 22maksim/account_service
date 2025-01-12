package com.example.account_service.model;

import com.example.account_service.model.cashback.tariff.TariffCashback;
import com.example.account_service.model.enums.AccountStatus;
import com.example.account_service.model.enums.Currency;
import com.example.account_service.model.enums.TariffType;
import com.example.account_service.model.enums.TypeNumber;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;

@Builder
@Table(name = "account")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private Owner owner;

    @OneToOne
    @JoinColumn(name = "balance_id", referencedColumnName = "id")
    private Balance balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeNumber type;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @Column(name = "close_at")
    private Timestamp closeAt;

    @Column(name = "tariff_type")
    private TariffType tariffType;

    @ManyToOne
    @JoinColumn(name = "tariff_cashback_id", nullable = false)
    private TariffCashback tariffCashback;

    @Version
    private int version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return new EqualsBuilder()
                .append(id, account.id)
                .append(type, account.type)
                .append(currency, account.currency)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(type)
                .append(currency)
                .toHashCode();
    }
}
