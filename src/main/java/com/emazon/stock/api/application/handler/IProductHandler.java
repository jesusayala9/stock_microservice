package com.emazon.stock.api.application.handler;

import com.emazon.stock.api.application.dto.ProductRequest;
import com.emazon.stock.api.application.dto.ProductResponse;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;


public interface IProductHandler {

    void saveProduct(ProductRequest productRequest);
    PagedResult<ProductResponse> getAllProducts(
            int page,
            int size,
            String sortBy,
            String direction,
            String name,
            String brand,
            String categories
    );


}



