package com.emazon.stock.api.domain.spi;

import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;

public interface IProductPersistencePort {
    void saveProduct(Product product);
    boolean existsByName(String name);

    PagedResult<Product> getAllProducts(
            Pagination pagination,
            SortCriteria sortCriteria,
            String name,
            String brand,
            String categories
    );
}
