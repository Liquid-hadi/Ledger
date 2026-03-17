package com.proj.personalfinancetracker.presentation.rest;

import com.proj.personalfinancetracker.model.transaction.*;
import com.proj.personalfinancetracker.presentation.rest.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Manage Income and expense transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Get all transactions")
    public ResponseEntity<PagedTransactionResponse> getAll(@ModelAttribute TransactionFilterRequest filter){
        return ResponseEntity.ok(transactionService.getFiltered(filter));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by id")
    public ResponseEntity<TransactionResponseModel> getById(@RequestParam Long id){
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new Transaction")
    public ResponseEntity<TransactionResponseModel> create(@Valid @RequestBody TransactionRequestModel request){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing transaction")
    public ResponseEntity<TransactionResponseModel> update(@RequestParam Long id, @Valid @RequestBody TransactionRequestModel request){
        return ResponseEntity.ok(transactionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a transaction")
    public ResponseEntity<Void> delete(@RequestParam Long id){
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
