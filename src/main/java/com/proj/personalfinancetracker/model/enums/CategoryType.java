package com.proj.personalfinancetracker.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CategoryType {
    INCOME ("Income"),
    EXPENSE("Expense");

    private final String value;

    CategoryType(String value){this.value = value;}

    @JsonCreator
    public static CategoryType fromValue(String value) {
        for (CategoryType c : values()) {
            if (c.name().equalsIgnoreCase(value)) return c;
        }
        throw new IllegalArgumentException("Unknown TransactionType: " + value);
    }

    @Override
    public String toString(){return String.valueOf(value);}
}
