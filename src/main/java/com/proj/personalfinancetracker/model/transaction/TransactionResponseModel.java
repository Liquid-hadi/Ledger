package com.proj.personalfinancetracker.model.transaction;

import com.proj.personalfinancetracker.model.enums.Status;
import com.proj.personalfinancetracker.model.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionResponseModel {
    private Long id;
    private String description;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDate date;
    private String categoryName;
    private String notes;
    private Status status;
}
