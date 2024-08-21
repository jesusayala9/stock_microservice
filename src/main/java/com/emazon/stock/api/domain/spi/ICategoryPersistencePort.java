package com.emazon.stock.api.domain.spi;

import com.emazon.stock.api.domain.model.Category;




public interface ICategoryPersistencePort {

    void saveCategory(Category category);

    boolean existsByName(String name);


}
