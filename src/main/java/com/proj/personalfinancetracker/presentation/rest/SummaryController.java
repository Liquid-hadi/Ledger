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

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
@Tag(name = "Summary", description = "Monthly financial summaries")
public class SummaryController {
    private final SummaryService service;

    @GetMapping
    @Operation(summary = "Get monthly summary", description = "Returns total income, expenses and net balance for a given month")
    private ResponseEntity<MonthlySummaryResponseModel> getMonthlySummary(@RequestParam int year, @RequestParam int month){
        return ResponseEntity.ok(service.getMonthlySummary(year, month));
    }
}
