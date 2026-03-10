package com.proj.personalfinancetracker.presentation.rest.summary.impl;

import com.proj.personalfinancetracker.external.db.financedb.myfinance.repository.TransactionRepo;
import com.proj.personalfinancetracker.model.monthlysummary.MonthlySummaryResponseModel;
import com.proj.personalfinancetracker.presentation.rest.summary.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final TransactionRepo transactionRepo;

    @Override
    public MonthlySummaryResponseModel getMonthlySummary(int year, int month) {
        BigDecimal totalIncome = transactionRepo.sumIncomeByMonth(year, month);
        BigDecimal totalExpense = transactionRepo.sumExpensesByMonth(year, month);

        MonthlySummaryResponseModel summary = new MonthlySummaryResponseModel();
        summary.setYear(year);
        summary.setMonth(month);
        summary.setTotalIncome(totalIncome);
        summary.setTotalExpense(totalExpense);
        summary.setNetBalance(totalIncome.subtract(totalExpense));
        return summary;
    }
}
