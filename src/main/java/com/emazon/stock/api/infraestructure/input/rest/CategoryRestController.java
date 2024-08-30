package com.emazon.stock.api.infraestructure.input.rest;
import com.emazon.stock.api.application.dto.CategoryRequest;
import com.emazon.stock.api.application.dto.CategoryResponse;
import com.emazon.stock.api.application.handler.ICategoryHandler;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController  {

    private final ICategoryHandler categoryHandler;

    @GetMapping("/all")
    public ResponseEntity<PagedResult<CategoryResponse>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        PagedResult<CategoryResponse> pagedCategories = categoryHandler.getAllCategories(page, size, sortBy, direction.toUpperCase());
        return ResponseEntity.ok(pagedCategories);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        categoryHandler.saveCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




}
