package com.emazon.stock.api.domain.api;

import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;


public interface IBrandServicePort {
    void saveBrand(Brand brand);

    PagedResult<Brand> getAllBrands(Pagination pagination, SortCriteria sortCriteria);



}
