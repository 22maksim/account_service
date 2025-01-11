package com.example.account_service.service.cashback.operation_type;

import com.example.account_service.model.dto.cashback.mapping.operation_type.OperationTypeByPercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.operation_type.OperationTypeByPercentResponseDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface MappingOperationTypeByPercentService {
    OperationTypeByPercentResponseDto addMappingOperationTypeByPercent(OperationTypeByPercentRequestDto requestDto);

    OperationTypeByPercentResponseDto getMappingOperationTypeByPercent(@NotNull @Positive Long id);

    void deleteMappingOperationTypeByPercent(@NotNull @Positive Long id);
}
