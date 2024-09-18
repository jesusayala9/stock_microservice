package com.emazon.stock.api.application.handler;

import com.emazon.stock.api.application.dto.ProductRequest;
import com.emazon.stock.api.application.dto.ProductResponse;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;



public interface IProductHandler {

    void saveProduct(ProductRequest productRequest);


    PagedResult<ProductResponse> getAllProducts(Pagination pagination, SortCriteria sortCriteria);
}



