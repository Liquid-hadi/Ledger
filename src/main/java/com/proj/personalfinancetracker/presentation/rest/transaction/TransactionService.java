package com.proj.personalfinancetracker.presentation.rest.transaction;

import com.proj.personalfinancetracker.model.transaction.*;

public interface TransactionService {
    TransactionListModel getAll();
    TransactionResponseModel getById(Long id);
    PagedTransactionResponse getFiltered(TransactionFilterRequest request);
    TransactionResponseModel create(TransactionRequestModel request);
    TransactionResponseModel update(Long Id, TransactionRequestModel request);
    void delete(Long id);
}
