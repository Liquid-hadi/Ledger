package com.proj.personalfinancetracker.presentation.rest.category;

import com.proj.personalfinancetracker.model.category.CategoryListModel;
import com.proj.personalfinancetracker.model.category.CategoryRequestModel;
import com.proj.personalfinancetracker.model.category.CategoryResponseModel;

public interface CategoryService {
    CategoryListModel getAll();
    CategoryResponseModel getById(Long id);
    CategoryResponseModel create(CategoryRequestModel request);
    CategoryResponseModel update(Long id, CategoryRequestModel request);
    void delete(Long id);
}
