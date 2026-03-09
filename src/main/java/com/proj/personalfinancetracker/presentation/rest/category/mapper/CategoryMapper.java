package com.proj.personalfinancetracker.presentation.rest.category.mapper;

import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.CategoryEntity;
import com.proj.personalfinancetracker.model.category.CategoryRequestModel;
import com.proj.personalfinancetracker.model.category.CategoryResponseModel;

public interface CategoryMapper {
    CategoryResponseModel toResponse(CategoryEntity categoryEntity);
    CategoryEntity toEntity(CategoryRequestModel categoryRequestModel);
}
