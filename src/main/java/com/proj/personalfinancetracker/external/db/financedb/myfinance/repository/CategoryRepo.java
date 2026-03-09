package com.proj.personalfinancetracker.external.db.financedb.myfinance.repository;

import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.CategoryEntity;
import com.proj.personalfinancetracker.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByStatus(Status status);

    Optional<CategoryEntity> findByIdAndStatus(Long id, Status status);

}
