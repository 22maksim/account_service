package com.example.account_service.model;

import com.example.account_service.model.enums.TypeNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeAccountNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = ".{20}", message = "The number length must be 20 characters.")
    @Column(name = "account_number", unique=true, nullable=false)
    private String account_number;

    @Column(name = "type", nullable=false)
    @Enumerated(EnumType.STRING)
    private TypeNumber type;
}
