package com.proj.personalfinancetracker.model.enums;


public enum Status {
    ACTIVE("Active"),
    DELETED("Active");

    private final String value;

    Status(String value) {this.value = value;}

    @Override
    public String toString(){return String.valueOf(value);}

}
