package com.proj.personalfinancetracker.presentation.rest.summary;

import com.proj.personalfinancetracker.model.monthlysummary.MonthlySummaryResponseModel;

import java.time.LocalDate;

public interface SummaryService {
    MonthlySummaryResponseModel getSummary(LocalDate start, LocalDate end);
}
