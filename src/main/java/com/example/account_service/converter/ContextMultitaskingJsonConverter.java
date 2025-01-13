package com.example.account_service.converter;

import com.example.account_service.exeption.UncorrectedValueRequest;
import com.example.account_service.model.multitasking.queries.ContextMultitasking;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ContextMultitaskingJsonConverter implements AttributeConverter<ContextMultitasking, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ContextMultitasking attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException ex) {
            throw new UncorrectedValueRequest("Error conversion Context to String. Error: " + ex);
        }
    }

    @Override
    public ContextMultitasking convertToEntityAttribute(String dbData) {
        return dbData == null ? null : mapper.convertValue(dbData, ContextMultitasking.class);
    }
}
