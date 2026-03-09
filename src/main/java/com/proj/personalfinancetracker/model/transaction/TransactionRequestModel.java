package com.proj.personalfinancetracker.model.transaction;

import com.proj.personalfinancetracker.model.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequestModel {

    @NotBlank
    private String description;

    @NonNull
    @Positive
    private BigDecimal amount;

    @NonNull
    private TransactionType type;

    @NonNull
    private LocalDate date;

    private Long categoryId;

    private String notes;
}
