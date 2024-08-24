package com.emazon.stock.api.domain.spi;

import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.utils.PagedResult;
import com.emazon.stock.api.domain.utils.Pagination;
import com.emazon.stock.api.domain.utils.SortCriteria;



public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean existsByName(String name);

    PagedResult<Category> getAllCategories(Pagination pagination, SortCriteria sortCriteria);

}
