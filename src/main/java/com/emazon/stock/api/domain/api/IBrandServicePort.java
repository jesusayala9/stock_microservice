package com.emazon.stock.api.domain.api;

import com.emazon.stock.api.domain.model.Brand;

import com.emazon.stock.api.domain.utils.PagedResult;
import com.emazon.stock.api.domain.utils.Pagination;
import com.emazon.stock.api.domain.utils.SortCriteria;


public interface IBrandServicePort {
    void saveBrand(Brand brand);

    PagedResult<Brand> getAllBrands(Pagination pagination, SortCriteria sortCriteria);

}
