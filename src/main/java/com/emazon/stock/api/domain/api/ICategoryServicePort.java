package com.emazon.stock.api.domain.api;

import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.utils.PagedResult;
import com.emazon.stock.api.domain.utils.Pagination;
import com.emazon.stock.api.domain.utils.SortCriteria;



public interface ICategoryServicePort {
    void saveCategory(Category category);
    PagedResult<Category> getAllCategories(Pagination pagination, SortCriteria sortCriteria);
}


