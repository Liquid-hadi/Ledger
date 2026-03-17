package com.proj.personalfinancetracker.model.enums;

public enum CategoryType {
    INCOME ("Income"),
    EXPENSE("Expense");

    private final String value;

    CategoryType(String value){this.value = value;}

    @Override
    public String toString(){return String.valueOf(value);}
}
