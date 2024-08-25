package com.emazon.stock.api.domain.usecase;

import com.emazon.stock.api.domain.api.IBrandServicePort;
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
        validateBrand(brand);
        brandPersistencePort.saveBrand(brand);
    }


    private void validateBrand(Brand brand) {
        if (brand.getName() == null || brand.getName().trim().isEmpty()) {
            throw new GlobalBrandException("Nombre no puede ser vacio");
        }
        if (brand.getName().length() > 50) {
            throw new GlobalBrandException("El nombre es muy largo");
        }
        if (brand.getDescription() == null || brand.getDescription().trim().isEmpty()) {
            throw new GlobalBrandException("Descripcion no puede ser vacio");
        }
        if (brand.getDescription().length() > 90) {
            throw new GlobalBrandException("Descripcion es muy larga");
        }

        if (brandPersistencePort.existsByName(brand.getName())) {
            throw new GlobalBrandException("Marca ya existe");
        }

    }
}
