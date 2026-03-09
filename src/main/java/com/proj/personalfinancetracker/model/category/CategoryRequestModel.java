package com.proj.personalfinancetracker.model.category;

import com.proj.personalfinancetracker.model.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class CategoryRequestModel {

    @NotBlank
    private String name;

    @NonNull
    private CategoryType type;
}
