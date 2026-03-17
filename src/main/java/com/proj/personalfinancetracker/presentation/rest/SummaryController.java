package com.proj.personalfinancetracker.presentation.rest;

import com.proj.personalfinancetracker.model.monthlysummary.MonthlySummaryResponseModel;
import com.proj.personalfinancetracker.presentation.rest.summary.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
@Tag(name = "Summary", description = "Date range financial summaries")
public class SummaryController {
    private final SummaryService service;

    @GetMapping
    @Operation(summary = "Get summary by date range", description = "Returns total income, expenses and net balance for a given date range")
    private ResponseEntity<MonthlySummaryResponseModel> getSummary(@RequestParam String startDate, @RequestParam String endDate){
        return ResponseEntity.ok(service.getSummary(LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }
}
