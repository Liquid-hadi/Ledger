package com.proj.personalfinancetracker.presentation.rest.transaction.mapper.impl;

import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.TransactionEntity;
import com.proj.personalfinancetracker.model.transaction.TransactionResponseModel;
import com.proj.personalfinancetracker.presentation.rest.transaction.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionResponseModel toResponse(TransactionEntity entity) {
        TransactionResponseModel res = new TransactionResponseModel();
        res.setId(entity.getId());
        res.setDescription(entity.getDescription());
        res.setAmount(entity.getAmount());
        res.setType(entity.getType());
        res.setDate(entity.getDate());
        res.setNotes(entity.getNotes());
        res.setStatus(entity.getStatus());
        res.setCategoryName(entity.getCategory().getName());
        return res;
    }

    @Override
    public TransactionEntity toEntity(TransactionResponseModel responseModel) {
        return null;
    }
}
