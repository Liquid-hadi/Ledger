package com.proj.personalfinancetracker.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TransactionType {
    INCOME ("Income"),
    EXPENSE("Expense");

    private final String value;

    TransactionType(String value){this.value = value;}

    @JsonCreator
    public static TransactionType fromValue(String value) {
        for (TransactionType t : values()) {
            if (t.name().equalsIgnoreCase(value)) return t;
        }
        throw new IllegalArgumentException("Unknown TransactionType: " + value);
    }

    @Override
    public String toString(){return String.valueOf(value);}
}
