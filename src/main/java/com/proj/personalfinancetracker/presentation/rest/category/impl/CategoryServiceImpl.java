package com.proj.personalfinancetracker.presentation.rest.category.impl;

import com.proj.personalfinancetracker.model.enums.Status;
import com.proj.personalfinancetracker.presentation.rest.category.mapper.CategoryMapper;
import com.proj.personalfinancetracker.presentation.rest.category.CategoryService;
import com.proj.personalfinancetracker.external.db.financedb.myfinance.entity.CategoryEntity;
import com.proj.personalfinancetracker.model.category.CategoryListModel;
import com.proj.personalfinancetracker.model.category.CategoryRequestModel;
import com.proj.personalfinancetracker.model.category.CategoryResponseModel;
import com.proj.personalfinancetracker.external.db.financedb.myfinance.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepo categoryRepo;

    @Override
    public CategoryListModel getAll() {
        return new CategoryListModel(
                categoryRepo.findAllByStatus(Status.ACTIVE).stream()
                        .map(categoryMapper::toResponse).toList()
        );
    }

    @Override
    public CategoryResponseModel getById(Long id) {
        return categoryMapper.toResponse(findById(id));
    }

    @Override
    public CategoryResponseModel create(CategoryRequestModel request) {
        return categoryMapper.toResponse(
                categoryRepo.save(categoryMapper.toEntity(request))
        );
    }

    @Override
    public CategoryResponseModel update(Long id, CategoryRequestModel request) {
        CategoryEntity category = findById(id);
        category.setName(request.getName());
        category.setType(request.getType());
        return categoryMapper.toResponse(
                categoryRepo.save(category));
    }

    @Override
    public void delete(Long id) {
        CategoryEntity category = findById(id);
        category.setStatus(Status.DELETED);
        categoryRepo.save(category);
    }

    //----------------------Helpers---------------

    private CategoryEntity findById(Long id){
        return categoryRepo.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Category not found " + id));
    }
}
