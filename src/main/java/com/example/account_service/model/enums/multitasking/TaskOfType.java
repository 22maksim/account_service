package com.example.account_service.model.enums.multitasking;

import lombok.Getter;

@Getter
public enum TaskOfType {
    OWNER_VERIFICATION(1),
    ACCOUNT_OPEN(2),
    CASHBACK(3),
    NOTIFICATION(4);

    private final int priority;

    TaskOfType(int priority) {
        this.priority = priority;
    }
}
