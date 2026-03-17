package com.proj.personalfinancetracker.presentation.rest.transaction.impl;

import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.CategoryEntity;
import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.TransactionEntity;
import com.proj.personalfinancetracker.external.db.financedb.myfinance.repository.CategoryRepo;
import com.proj.personalfinancetracker.external.db.financedb.myfinance.repository.TransactionRepo;
import com.proj.personalfinancetracker.model.enums.Status;
import com.proj.personalfinancetracker.model.transaction.*;
import com.proj.personalfinancetracker.presentation.rest.transaction.TransactionService;
import com.proj.personalfinancetracker.presentation.rest.transaction.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PagedTransactionResponse getFiltered(TransactionFilterRequest filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());

        Page<TransactionEntity> page = transactionRepo.findWithFilters(
                filter.getType(),
                filter.getCategoryId(),
                filter.getStartDate(),
                filter.getEndDate(),
                pageable
        );
        PagedTransactionResponse response = new PagedTransactionResponse();

        response.setTransactions(page.getContent().stream().map(transactionMapper::toResponse).toList());

        response.setCurrentPage(page.getNumber());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setSize(page.getSize());

        return response;
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
