package com.proj.personalfinancetracker.model.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoryListModel {
    private List<CategoryResponseModel> categories;
    private int count;

    public CategoryListModel(List<CategoryResponseModel> categories){
        this.categories = categories;
        this.count = categories.size();
    }
}
