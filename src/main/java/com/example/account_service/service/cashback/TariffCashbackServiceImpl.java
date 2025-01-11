package com.example.account_service.service.cashback;

import com.example.account_service.exeption.DataCashbackException;
import com.example.account_service.mapper.cashback.TariffCashbackMapper;
import com.example.account_service.model.cashback.tariff.TariffCashback;
import com.example.account_service.model.cashback.tariff.TariffMerchantPercents;
import com.example.account_service.model.cashback.tariff.TariffOperationTypePercents;
import com.example.account_service.model.dto.cashback.RequestTariffCashbackDto;
import com.example.account_service.model.dto.cashback.ResponseTariffCashbackDto;
import com.example.account_service.repository.cashback.tariff.TariffCashbackRepository;
import com.example.account_service.repository.cashback.tariff.TariffMerchantPercentsRepository;
import com.example.account_service.repository.cashback.tariff.TariffOperationTypePercentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class TariffCashbackServiceImpl implements TariffCashbackService {
    private final TariffCashbackRepository tariffCashbackRepository;
    private final TariffCashbackMapper tariffCashbackMapper;
    private final TariffMerchantPercentsRepository tariffMerchantPercentsRepository;
    private final TariffOperationTypePercentsRepository tariffOperationTypePercentsRepository;

    @Transactional
    @Override
    public ResponseTariffCashbackDto createdCashback(RequestTariffCashbackDto requestTariffCashbackDto) {
        if (requestTariffCashbackDto == null) {
            log.error("requestTariffCashbackDto is null");
            throw new DataCashbackException("requestTariffCashbackDto is null");
        }

        List<TariffMerchantPercents> tariffMerchantPercentsList = new ArrayList<>();
        if (requestTariffCashbackDto.getTariffMerchantPercentIds() != null) {
            tariffMerchantPercentsList = tariffMerchantPercentsRepository
                    .findAllById(requestTariffCashbackDto.getTariffMerchantPercentIds());
        } else {
            log.info("tariffMerchantPercentsList is null");
        }

        List<TariffOperationTypePercents> tariffOperationTypePercentsList = new ArrayList<>();
        if (requestTariffCashbackDto.getTariffOperationTypePercentsIds() != null) {
            tariffOperationTypePercentsList = tariffOperationTypePercentsRepository
                    .findAllById(requestTariffCashbackDto.getTariffOperationTypePercentsIds());
        }

        TariffCashback tariffCashback = TariffCashback.builder()
                .description(requestTariffCashbackDto.getDescription())
                .created(Instant.now())
                .tariffMerchantPercents(tariffMerchantPercentsList)
                .tariffOperationTypePercents(tariffOperationTypePercentsList)
                .build();

        tariffCashback = tariffCashbackRepository.save(tariffCashback);
        return tariffCashbackMapper.toResponseDto(tariffCashback);
    }

    @Transactional
    @Override
    public ResponseTariffCashbackDto getCashbackTariff(Long id) {
        TariffCashback tariffCashback = tariffCashbackRepository.findById(id)
                .orElseThrow(() -> new DataCashbackException("TariffCashback not found"));
        return tariffCashbackMapper.toResponseDto(tariffCashback);
    }
}
