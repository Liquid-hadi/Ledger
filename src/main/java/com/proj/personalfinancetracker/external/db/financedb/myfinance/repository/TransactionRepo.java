package com.proj.personalfinancetracker.external.db.financedb.myfinance.repository;


import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllByOrderByDateDesc();

    List<TransactionEntity> findByCategoryId(Long categoryId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.type = 'INCOME' AND YEAR(t.date) = :year AND MONTH(t.date) = :month")
    BigDecimal sumIncomeByMonth();

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.type = 'EXPENSE' AND YEAR(t.date) = :year AND MONTH(t.date) = :month")
    BigDecimal sumExpensesByMonth();



}
