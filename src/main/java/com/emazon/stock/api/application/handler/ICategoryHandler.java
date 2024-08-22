package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.CategoryRequest;
import com.emazon.stock.api.application.dto.CategoryResponse;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface ICategoryHandler {
    void saveCategory(CategoryRequest categoryRequest);

    Page<CategoryResponse> getAllCategories(Pageable pageable);

}