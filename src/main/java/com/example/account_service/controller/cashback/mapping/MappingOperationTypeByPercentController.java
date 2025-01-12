package com.example.account_service.controller.cashback.mapping;

import com.example.account_service.model.dto.cashback.mapping.operation_type.OperationTypeByPercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.operation_type.OperationTypeByPercentResponseDto;
import com.example.account_service.service.cashback.operation_type.MappingOperationTypeByPercentService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mapping-operation-type-by-percent")
@RequiredArgsConstructor
public class MappingOperationTypeByPercentController {
    private final MappingOperationTypeByPercentService mappingOperationTypeByPercentServiceImpl;

    @PostMapping
    public OperationTypeByPercentResponseDto addMappingOperationTypeByPercent(
            @RequestBody @NotNull OperationTypeByPercentRequestDto requestDto) {
        return mappingOperationTypeByPercentServiceImpl.addMappingOperationTypeByPercent(requestDto);
    }

    @GetMapping("/{id}")
    public OperationTypeByPercentResponseDto getMappingOperationTypeByPercent(@PathVariable @NotNull @Positive Long id) {
        return mappingOperationTypeByPercentServiceImpl.getMappingOperationTypeByPercent(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMappingOperationTypeByPercent(@PathVariable @NotNull @Positive Long id) {
        mappingOperationTypeByPercentServiceImpl.deleteMappingOperationTypeByPercent(id);
    }
}
