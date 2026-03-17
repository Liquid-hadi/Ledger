package com.proj.personalfinancetracker.external.db.financedb.myfinance.repository;


import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.TransactionEntity;
import com.proj.personalfinancetracker.model.enums.Status;
import com.proj.personalfinancetracker.model.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, Long>{

    List<TransactionEntity> findAllByStatus(Status status);
    Optional<TransactionEntity> findByIdAndStatus(Long id, Status status);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionEntity t WHERE t.type = 'INCOME' AND t.date >= :startDate AND t.date <= :endDate AND t.status = 'ACTIVE'")
    BigDecimal sumIncomeByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionEntity t WHERE t.type = 'EXPENSE' AND t.date >= :startDate AND t.date <= :endDate AND t.status = 'ACTIVE'")
    BigDecimal sumExpensesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT t FROM TransactionEntity t WHERE t.status = 'ACTIVE' AND (CAST(:type AS string) IS NULL OR t.type = :type) AND (CAST(:categoryId AS long) IS NULL OR t.category.id = :categoryId) AND (CAST(:startDate AS date) IS NULL OR t.date >= :startDate) AND (CAST(:endDate AS date) IS NULL OR t.date <= :endDate) ORDER BY t.date DESC")
    Page<TransactionEntity> findWithFilters(
            @Param("type") TransactionType type,
            @Param("categoryId") Long categoryId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);
}