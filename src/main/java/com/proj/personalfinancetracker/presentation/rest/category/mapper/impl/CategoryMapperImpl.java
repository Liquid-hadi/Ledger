package com.proj.personalfinancetracker.presentation.rest.category.mapper.impl;

import com.proj.personalfinancetracker.presentation.rest.category.mapper.CategoryMapper;
import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.CategoryEntity;
import com.proj.personalfinancetracker.model.category.CategoryRequestModel;
import com.proj.personalfinancetracker.model.category.CategoryResponseModel;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public CategoryResponseModel toResponse(CategoryEntity categoryEntity) {
        CategoryResponseModel res = new CategoryResponseModel();
        res.setId(categoryEntity.getId());
        res.setName(categoryEntity.getName());
        res.setType(categoryEntity.getType());
        return res;
    }

    @Override
    public CategoryEntity toEntity(CategoryRequestModel categoryRequestModel) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(categoryRequestModel.getName());
        entity.setType(categoryRequestModel.getType());
        return entity;
    }
}
