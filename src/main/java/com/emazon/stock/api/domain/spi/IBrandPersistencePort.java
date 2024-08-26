package com.emazon.stock.api.domain.spi;

import com.emazon.stock.api.domain.model.Brand;

import com.emazon.stock.api.domain.utils.PagedResult;
import com.emazon.stock.api.domain.utils.Pagination;
import com.emazon.stock.api.domain.utils.SortCriteria;


public interface IBrandPersistencePort {

    void saveBrand(Brand category);
    boolean existsByName(String name);

    PagedResult<Brand> getAllBrands(Pagination pagination, SortCriteria sortCriteria);
}
