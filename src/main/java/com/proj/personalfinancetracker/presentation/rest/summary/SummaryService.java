package com.proj.personalfinancetracker.presentation.rest.summary;

import com.proj.personalfinancetracker.model.monthlysummary.MonthlySummaryResponseModel;

public interface SummaryService {
    MonthlySummaryResponseModel getMonthlySummary(int year, int month);
}
