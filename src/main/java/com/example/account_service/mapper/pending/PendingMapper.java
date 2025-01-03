package com.example.account_service.mapper.pending;

import com.example.account_service.model.Pending;
import com.example.account_service.model.dto.payment.PaymentRequestDto;
import com.example.account_service.model.dto.payment.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PendingMapper {

    Pending requestDtoToPending(PaymentRequestDto requestDto);

    @Mapping(target = "requestId", ignore = true)
    PaymentResponseDto pendingToResponseDto(Pending pending);
}
