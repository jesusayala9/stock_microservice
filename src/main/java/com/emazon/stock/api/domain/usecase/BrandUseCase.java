package com.emazon.stock.api.domain.usecase;

import com.emazon.stock.api.domain.api.IBrandServicePort;
import com.emazon.stock.api.domain.exception.EntityAlreadyExistsException;
import com.emazon.stock.api.domain.exception.GlobalBrandException;
import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.domain.spi.IBrandPersistencePort;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand) {
        if (brandPersistencePort.existsByName(brand.getName())) {
            throw new EntityAlreadyExistsException("Marca");
        }
        brandPersistencePort.saveBrand(brand);
    }



}
