package com.proj.personalfinancetracker.presentation.rest.transaction;

import com.proj.personalfinancetracker.model.transaction.TransactionListModel;
import com.proj.personalfinancetracker.model.transaction.TransactionRequestModel;
import com.proj.personalfinancetracker.model.transaction.TransactionResponseModel;

public interface TransactionService {
    TransactionListModel getAll();
    TransactionResponseModel getById(Long id);
    TransactionResponseModel create();
    TransactionResponseModel update(Long Id, TransactionRequestModel request);
    void delete(Long id);
}
