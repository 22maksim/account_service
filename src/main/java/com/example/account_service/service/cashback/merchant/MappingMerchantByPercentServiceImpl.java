package com.example.account_service.service.cashback.merchant;

import com.example.account_service.exeption.DataCashbackException;
import com.example.account_service.mapper.cashback.mapping.merchant.MerchantPercentMapper;
import com.example.account_service.model.cashback.tariff.MerchantPercent;
import com.example.account_service.model.dto.cashback.mapping.merchant.MerchantPercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.merchant.MerchantPercentResponseDto;
import com.example.account_service.repository.cashback.tariff.MerchantPercentRepository;
import com.example.account_service.repository.cashback.tariff.MerchantRepository;
import com.example.account_service.repository.cashback.tariff.PercentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MappingMerchantByPercentServiceImpl implements MappingMerchantByPercentService {
    private final MerchantPercentRepository merchantPercentRepository;
    private final MerchantRepository merchantRepository;
    private final PercentsRepository percentsRepository;
    private final MerchantPercentMapper merchantPercentMapper;

    @Transactional
    @Override
    public MerchantPercentResponseDto addMappingMerchantByPercent(MerchantPercentRequestDto requestDto) {
        if (requestDto == null) {
            log.error("merchantPercentRequestDto is null");
            throw new DataCashbackException("merchantPercentRequestDto is null");
        }
        MerchantPercent merchantPercent = MerchantPercent.builder()
                .merchant(merchantRepository.findById(requestDto.getMerchantId())
                        .orElseThrow(() -> new DataCashbackException("Merchant not found")))
                .percents(percentsRepository.findById(requestDto.getPercentsId())
                        .orElseThrow(() -> new DataCashbackException("Percent not found")))
                .build();
        merchantPercent = merchantPercentRepository.save(merchantPercent);
        return merchantPercentMapper.toResponseDto(merchantPercent);
    }

    @Transactional
    @Override
    public MerchantPercentResponseDto getMappingMerchantByPercent(Long id) {
        MerchantPercent merchantPercent = merchantPercentRepository.findById(id)
                .orElseThrow(() -> new DataCashbackException("MerchantPercent not found. Id: " + id));
        return merchantPercentMapper.toResponseDto(merchantPercent);
    }

    @Transactional
    @Override
    public void deleteMappingMerchantByPercent(Long id) {
        merchantPercentRepository.deleteById(id);
    }
}
