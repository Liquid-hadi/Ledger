package com.proj.personalfinancetracker.presentation.rest.transaction.mapper;

import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.TransactionEntity;
import com.proj.personalfinancetracker.model.transaction.TransactionResponseModel;

public interface TransactionMapper {
    TransactionResponseModel toResponse(TransactionEntity entity);
    TransactionEntity toEntity(TransactionResponseModel responseModel);
}
