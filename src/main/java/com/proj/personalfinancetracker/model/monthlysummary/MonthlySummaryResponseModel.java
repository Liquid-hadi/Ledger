package com.proj.personalfinancetracker.model.monthlysummary;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonthlySummaryResponseModel {
    private int year;
    private int month;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netBalance;
}
