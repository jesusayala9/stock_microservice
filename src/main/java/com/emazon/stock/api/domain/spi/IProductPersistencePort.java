package com.emazon.stock.api.domain.spi;

import com.emazon.stock.api.domain.model.Product;

public interface IProductPersistencePort {
    void saveProduct(Product product);
    boolean existsByName(String name);
}
