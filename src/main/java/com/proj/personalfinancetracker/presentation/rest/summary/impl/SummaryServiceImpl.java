package com.proj.personalfinancetracker.presentation.rest.summary.impl;

import com.proj.personalfinancetracker.external.db.financedb.myfinance.repository.TransactionRepo;
import com.proj.personalfinancetracker.model.monthlysummary.MonthlySummaryResponseModel;
import com.proj.personalfinancetracker.presentation.rest.summary.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final TransactionRepo transactionRepo;

    @Override
    public MonthlySummaryResponseModel getSummary(LocalDate start, LocalDate end) {
        BigDecimal totalIncome = transactionRepo.sumIncomeByDateRange(start, end);
        BigDecimal totalExpense = transactionRepo.sumExpensesByDateRange(start, end);

        MonthlySummaryResponseModel summary = new MonthlySummaryResponseModel();
        summary.setStartDate(start);
        summary.setEndDate(end);

        summary.setTotalIncome(totalIncome);
        summary.setTotalExpense(totalExpense);
        summary.setNetBalance(totalIncome.subtract(totalExpense));
        return summary;
    }
}
