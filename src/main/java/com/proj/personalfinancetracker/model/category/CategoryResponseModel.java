package com.proj.personalfinancetracker.model.category;

import com.proj.personalfinancetracker.model.enums.CategoryType;
import lombok.Data;

@Data
public class CategoryResponseModel {
    private Long id;
    private String name;
    private CategoryType type;
}
