package com.emazon.stock.api.domain.spi;

import com.emazon.stock.api.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean existsByName(String name);

    Page<Category> getAllCategories(Pageable pageable);



}
