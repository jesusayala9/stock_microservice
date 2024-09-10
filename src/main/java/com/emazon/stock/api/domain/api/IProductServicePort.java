package com.emazon.stock.api.domain.api;

import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;

public interface IProductServicePort {
    void saveProduct(Product product);

    PagedResult<Product> getAllProducts(Pagination pagination, SortCriteria sortCriteria);
}
