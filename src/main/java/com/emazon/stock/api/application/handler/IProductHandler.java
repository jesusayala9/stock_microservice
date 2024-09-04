package com.emazon.stock.api.application.handler;

import com.emazon.stock.api.application.dto.ProductRequest;

public interface IProductHandler {

    void saveProduct(ProductRequest productRequest);
}
