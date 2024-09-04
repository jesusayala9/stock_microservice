package com.emazon.stock.api.domain.api;

import com.emazon.stock.api.domain.model.Product;

public interface IProductServicePort {
    void saveProduct(Product product);
}
