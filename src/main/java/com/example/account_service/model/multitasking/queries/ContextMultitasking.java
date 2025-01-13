package com.example.account_service.model.multitasking.queries;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContextMultitasking implements Serializable {
    private String accountId;
    private Long ownerId;
    private Long tariffCashbackId;
    private String accountRequestDto; // дессериализуем в AccountRequestDto
}
