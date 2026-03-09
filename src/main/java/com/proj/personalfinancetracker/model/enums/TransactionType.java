package com.proj.personalfinancetracker.model.enums;

public enum TransactionType {
    INCOME ("Income"),
    OUTCOME("Expense");

    private final String value;

    TransactionType(String value){this.value = value;}

    @Override
    public String toString(){return String.valueOf(value);}
}
