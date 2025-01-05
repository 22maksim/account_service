package com.example.account_service.service.account.cumulative.tariff;

import com.example.account_service.exeption.DataAccountException;
import com.example.account_service.model.CumulativeTariff;
import com.example.account_service.model.dto.cumulative.tariff.CumulativeTariffRequestDto;
import com.example.account_service.model.dto.cumulative.tariff.CumulativeTariffResponseDto;
import com.example.account_service.model.enums.TariffType;
import com.example.account_service.repository.tariff.cumulative.CumulativeTariffRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CumulativeTariffServiceImpl implements CumulativeTariffService {
    private final CumulativeTariffRepository cumulativeTariffRepository;
    private final ObjectMapper objectMapper;

    @Override
    public CumulativeTariffResponseDto createCumulativeTariff(CumulativeTariffRequestDto requestDto) {
        List<Integer> rates = List.of(requestDto.getRate());
        try {
            CumulativeTariff cumulativeTariff = cumulativeTariffRepository.save(CumulativeTariff.builder()
                    .type(TariffType.valueOf(requestDto.getType()))
                    .history(objectMapper.writeValueAsString(rates))
                    .build());

            return CumulativeTariffResponseDto.builder()
                    .type(requestDto.getType())
                    .historyRates(rates)
                    .updateAt(cumulativeTariff.getUpdateAt())
                    .build();
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
            throw new DataAccountException("Json Mapping Exception");
        }
    }

    @Override
    public CumulativeTariffResponseDto updateCumulativeTariff(CumulativeTariffRequestDto requestDto) {
        CumulativeTariff cumulativeTariff = cumulativeTariffRepository.findById(requestDto.getType())
                .orElseThrow(() -> new DataAccountException("Cumulative Tariff Not Found"));

        List<Integer> rateList = objectMapper.convertValue(cumulativeTariff.getHistory(), new TypeReference<>() {
        });
        rateList.add(requestDto.getRate());

        return CumulativeTariffResponseDto.builder()
                .type(requestDto.getType())
                .historyRates(rateList)
                .build();
    }

    @Override
    public List<CumulativeTariffResponseDto> getAllCumulativeTariffs(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        List<CumulativeTariff> tariffList = cumulativeTariffRepository.findAll(pageable).getContent();

        return tariffList.stream()
                .map(tariff -> CumulativeTariffResponseDto.builder()
                        .type(tariff.getType().name())
                        .historyRates(objectMapper.convertValue(tariff.getHistory(), new TypeReference<>() {}))
                        .updateAt(tariff.getUpdateAt())
                        .build())
                .toList();
    }
}
