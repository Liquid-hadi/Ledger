package com.proj.personalfinancetracker.presentation.rest.transaction.impl;

import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.CategoryEntity;
import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.TransactionEntity;
import com.proj.personalfinancetracker.external.db.financedb.myfinance.repository.CategoryRepo;
import com.proj.personalfinancetracker.external.db.financedb.myfinance.repository.TransactionRepo;
import com.proj.personalfinancetracker.model.enums.Status;
import com.proj.personalfinancetracker.model.transaction.TransactionListModel;
import com.proj.personalfinancetracker.model.transaction.TransactionRequestModel;
import com.proj.personalfinancetracker.model.transaction.TransactionResponseModel;
import com.proj.personalfinancetracker.presentation.rest.transaction.TransactionService;
import com.proj.personalfinancetracker.presentation.rest.transaction.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final CategoryRepo categoryRepo;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionListModel getAll() {
        return new TransactionListModel(
                transactionRepo.findAllByStatus(Status.ACTIVE).stream().map(transactionMapper::toResponse).toList()
        );
    }

    @Override
    public TransactionResponseModel getById(Long id) {
        return transactionMapper.toResponse(findById(id));
    }

    @Override
    public TransactionResponseModel create(TransactionRequestModel request) {
        TransactionEntity transaction = transactionMapper.toEntity(request);
        transaction.setCategory(categoryExists(request.getCategoryId()));
        return transactionMapper.toResponse(transactionRepo.save(transaction));
    }

    @Override
    public TransactionResponseModel update(Long id, TransactionRequestModel request) {
        TransactionEntity transaction = findById(id);
        transaction.setDescription(request.getDescription());
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDate(request.getDate());
        transaction.setNotes(request.getNotes());
        transaction.setCategory(categoryExists(request.getCategoryId()));
        return transactionMapper.toResponse(transactionRepo.save(transaction));
    }

    @Override
    public void delete(Long id) {
        TransactionEntity transaction = findById(id);
        transaction.setStatus(Status.DELETED);
        transactionRepo.save(transaction);
    }

    //----------------------Helpers---------------

    private TransactionEntity findById(Long id) {
        return transactionRepo.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new RuntimeException("transaction not found " + id));
    }

    //----------------------Validation---------------
    private CategoryEntity categoryExists(Long categoryId) {
        if (categoryId == null) return null;
        return categoryRepo.findByIdAndStatus(categoryId, Status.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
    }
}
