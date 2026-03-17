package com.proj.personalfinancetracker.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class PagedTransactionResponse {
    private List<TransactionResponseModel> transactions;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private int size;
}
