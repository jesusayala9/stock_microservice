package com.emazon.stock.api.domain.spi;

import com.emazon.stock.api.domain.model.Brand;


public interface IBrandPersistencePort {

    void saveBrand(Brand category);
    boolean existsByName(String name);
}
