package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.CategoryRequest;


public interface ICategoryHandler {
    void saveCategory(CategoryRequest categoryRequest);

}