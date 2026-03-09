package com.proj.personalfinancetracker.presentation.rest;

import com.proj.personalfinancetracker.model.category.CategoryListModel;
import com.proj.personalfinancetracker.model.category.CategoryRequestModel;
import com.proj.personalfinancetracker.model.category.CategoryResponseModel;
import com.proj.personalfinancetracker.presentation.rest.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoryListModel> getAll(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseModel> getById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getById(id));
    }

    //@Operation(summary = "Create new Task")
    @PostMapping
    public ResponseEntity<CategoryResponseModel> create(@Valid @RequestBody CategoryRequestModel request){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseModel> update(@PathVariable Long id,
                                                        @Valid @RequestBody CategoryRequestModel request){
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
