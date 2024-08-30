package com.emazon.stock.api.domain.spi;

import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;



public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean existsByName(String name);

    PagedResult<Category> getAllCategories(Pagination pagination, SortCriteria sortCriteria);

}
