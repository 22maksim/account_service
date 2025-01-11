package com.example.account_service.service.cashback;

import com.example.account_service.exeption.DataCashbackException;
import com.example.account_service.mapper.cashback.mapping.merchant.TariffCashbackByMerchantPercentMapper;
import com.example.account_service.mapper.cashback.mapping.type_operation.TariffOperationTypePercentMapper;
import com.example.account_service.model.cashback.tariff.TariffMerchantPercents;
import com.example.account_service.model.cashback.tariff.TariffOperationTypePercents;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.MappingTariffCashbackByMerchantPercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.MappingTariffCashbackByMerchantPercentResponseDto;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.TariffOperationTypePercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.TariffOperationTypePercentResponseDto;
import com.example.account_service.repository.cashback.tariff.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MappingTariffCashbackByOperationTypeAndByMerchantPercentServiceImpl
        implements MappingTariffCashbackByOperationTypeAndByMerchantPercentService {
    // Добавление маппинга для кешбека с продавцами, типами операций и их процентными ставками
    private final TariffOperationTypePercentsRepository tariffOperationTypePercentsRepository;
    private final TariffMerchantPercentsRepository tariffMerchantPercentsRepository;
    private final TariffOperationTypePercentMapper tariffOperationTypePercentMapper;
    private final TariffCashbackByMerchantPercentMapper tariffCashbackByMerchantPercentMapper;
    private final TariffCashbackRepository tariffCashbackRepository;
    private final OperationTypePercentRepository operationTypePercentRepository;
    private final MerchantPercentRepository merchantPercentRepository;

    @Transactional
    @Override
    public TariffOperationTypePercentResponseDto addTariffOperationTypePercent(
            TariffOperationTypePercentRequestDto requestDto) {
        if (requestDto == null) {
            log.error("requestDto is null");
            throw new IllegalArgumentException("requestDto is null");
        }
        TariffOperationTypePercents typePercents = TariffOperationTypePercents.builder()
                .tariffCashback(tariffCashbackRepository.findById(requestDto.getTariffCashbackId())
                        .orElseThrow(() -> new DataCashbackException("tariffCashback not found")))
                .operationTypePercent(operationTypePercentRepository.findById(requestDto.getTariffCashbackId())
                        .orElseThrow(() -> new DataCashbackException("operationTypePercent not found")))
                .build();

        typePercents = tariffOperationTypePercentsRepository.save(typePercents);
        return tariffOperationTypePercentMapper.toTariffOperationTypeResponseDto(typePercents);
    }

    @Transactional
    @Override
    public void deleteTariffOperationTypePercent(Long id) {
        tariffOperationTypePercentsRepository.deleteById(id);
    }

    @Transactional
    @Override
    public TariffOperationTypePercentResponseDto getTariffOperationTypePercent(Long id) {
        TariffOperationTypePercents typePercents = tariffOperationTypePercentsRepository.findById(id)
                .orElseThrow(() -> new DataCashbackException("Mapping not found"));
        return tariffOperationTypePercentMapper.toTariffOperationTypeResponseDto(typePercents);
    }

    @Transactional
    @Override
    public MappingTariffCashbackByMerchantPercentResponseDto addMappingTariffCashbackByMerchantPercent(
            MappingTariffCashbackByMerchantPercentRequestDto requestDto) {
        if (requestDto == null) {
            log.error("requestDto is null");
            throw new DataCashbackException("requestDto is null");
        }
        TariffMerchantPercents tariffMerchantPercents = TariffMerchantPercents.builder()
                .merchantPercent(merchantPercentRepository.findById(requestDto.getMerchantPercentId())
                        .orElseThrow(() -> new DataCashbackException("merchantPercent not found")))
                .tariffCashback(tariffCashbackRepository.findById(requestDto.getTariffCashbackId())
                        .orElseThrow(() -> new DataCashbackException("tariffCashback not found")))
                .build();
        tariffMerchantPercents = tariffMerchantPercentsRepository.save(tariffMerchantPercents);
        return tariffCashbackByMerchantPercentMapper.toResponseDto(tariffMerchantPercents);
    }

    @Transactional
    @Override
    public MappingTariffCashbackByMerchantPercentResponseDto getMappingTariffCashbackByMerchantPercent(
            Long id) {
        TariffMerchantPercents tariffMerchantPercents = tariffMerchantPercentsRepository.findById(id)
                .orElseThrow(() -> new DataCashbackException("Mapping not found"));
        return tariffCashbackByMerchantPercentMapper.toResponseDto(tariffMerchantPercents);
    }

    @Transactional
    @Override
    public void deleteMappingTariffCashbackByMerchantPercent(Long id) {
        tariffMerchantPercentsRepository.deleteById(id);
    }
}
