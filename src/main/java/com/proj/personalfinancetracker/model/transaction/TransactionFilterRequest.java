package com.proj.personalfinancetracker.model.transaction;

import com.proj.personalfinancetracker.model.enums.TransactionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionFilterRequest {
    private TransactionType type;
    private Long categoryId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int page = 0;
    private int size = 15;
}
