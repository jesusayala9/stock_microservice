package com.emazon.stock.api.domain.api;

import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;



public interface ICategoryServicePort {
    void saveCategory(Category category);
    PagedResult<Category> getAllCategories(Pagination pagination, SortCriteria sortCriteria);
}


