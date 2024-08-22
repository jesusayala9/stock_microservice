package com.emazon.stock.api.domain.api;

import com.emazon.stock.api.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;





public interface ICategoryServicePort {
    void saveCategory(Category category);
    Page<Category> getAllCategories(Pageable pageable);

}
