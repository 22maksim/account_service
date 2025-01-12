package com.example.account_service.model.dto.cashback.mapping.operation_type;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationTypeByPercentResponseDto {
    private Long id;
    private Long operationTypeId;
    private Long percentsId;
}
