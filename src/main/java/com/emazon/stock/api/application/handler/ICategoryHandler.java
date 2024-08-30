package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.CategoryRequest;
import com.emazon.stock.api.application.dto.CategoryResponse;


import com.emazon.stock.api.domain.utils.pagination.PagedResult;


public interface ICategoryHandler {
    void saveCategory(CategoryRequest categoryRequest);

    PagedResult<CategoryResponse> getAllCategories(int page, int size, String sortBy, String direction);

}