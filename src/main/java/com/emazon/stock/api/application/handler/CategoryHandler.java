package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.CategoryRequest;


import com.emazon.stock.api.application.dto.CategoryResponse;
import com.emazon.stock.api.application.mapper.CategoryRequestMapper;

import com.emazon.stock.api.application.mapper.CategoryResponseMapper;
import com.emazon.stock.api.domain.api.ICategoryServicePort;
import com.emazon.stock.api.domain.model.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler{
    private final ICategoryServicePort categoryServicePort;
    private final CategoryRequestMapper categoryRequestMapper;

    private final CategoryResponseMapper categoryResponseMapper;



    @Override
    public void saveCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.saveCategory(category);
    }

    @Override
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryServicePort.getAllCategories(pageable);
        return categoryPage.map(categoryResponseMapper::toResponse);
    }




}
