package com.example.account_service.service.cashback.operation_type;

import com.example.account_service.exeption.DataCashbackException;
import com.example.account_service.model.cashback.tariff.OperationTypePercent;
import com.example.account_service.model.dto.cashback.mapping.operation_type.OperationTypeByPercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.operation_type.OperationTypeByPercentResponseDto;
import com.example.account_service.repository.cashback.tariff.OperationTypePercentRepository;
import com.example.account_service.repository.cashback.tariff.PercentsRepository;
import com.example.account_service.repository.cashback.tariff.TypeOperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MappingOperationTypeByPercentServiceImpl implements MappingOperationTypeByPercentService {
    private final OperationTypePercentRepository operationTypePercentRepository;
    private final TypeOperationRepository typeOperationRepository;
    private final PercentsRepository percentsRepository;

    @Transactional
    @Override
    public OperationTypeByPercentResponseDto addMappingOperationTypeByPercent(OperationTypeByPercentRequestDto requestDto) {
        if (requestDto == null) {
            log.error("requestDto is null");
            throw new DataCashbackException("requestDto is null");
        }
        OperationTypePercent operationTypePercent = OperationTypePercent.builder()
                .typeOperation(typeOperationRepository.findById(requestDto.getTypeOperation())
                        .orElseThrow(() -> new DataCashbackException("Type Operation Not Found")))
                .percents(percentsRepository.findById(requestDto.getPercentsId()).orElseThrow(
                        () -> new DataCashbackException("Percents Id Not Found")
                ))
                .build();
        operationTypePercent = operationTypePercentRepository.save(operationTypePercent);
        return OperationTypeByPercentResponseDto.builder()
                .id(operationTypePercent.getId())
                .operationTypeId(operationTypePercent.getTypeOperation().getId())
                .percentsId(operationTypePercent.getPercents().getId())
                .build();
    }

    @Transactional
    @Override
    public OperationTypeByPercentResponseDto getMappingOperationTypeByPercent(Long id) {
        OperationTypePercent operationTypePercent = operationTypePercentRepository.findById(id)
                .orElseThrow(() -> new DataCashbackException("Operation Type Not Found"));
        return OperationTypeByPercentResponseDto.builder()
                .id(operationTypePercent.getId())
                .operationTypeId(operationTypePercent.getTypeOperation().getId())
                .percentsId(operationTypePercent.getPercents().getId())
                .build();
    }

    @Transactional
    @Override
    public void deleteMappingOperationTypeByPercent(Long id) {
        operationTypePercentRepository.deleteById(id);
    }
}
