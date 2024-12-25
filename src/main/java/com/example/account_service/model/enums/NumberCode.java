package com.example.account_service.model.enums;

public enum NumberCode {
    DEBIT(4500_0000_0000_0000L),
    CREDIT(4600_0000_0000_0000L),
    CUMULATIVE(4700_0000_0000_0000L),;

    private final long value;

    NumberCode(long value) {
        this.value = value;
    }

    public static long getCode(String type) {
        if (type == null) {
            throw new NullPointerException("type is null");
        }
        for (NumberCode code : NumberCode.values()) {
            if (code.name().equals(type)) {
                return code.value;
            }
        }
        throw new IllegalArgumentException("No such number code: " + type);
    }
}
