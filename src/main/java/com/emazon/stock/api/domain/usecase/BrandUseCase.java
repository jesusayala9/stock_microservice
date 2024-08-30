package com.emazon.stock.api.domain.usecase;

import com.emazon.stock.api.domain.api.IBrandServicePort;


import com.emazon.stock.api.domain.exception.EntityAlreadyExistsException;


import com.emazon.stock.api.domain.model.Brand;

import com.emazon.stock.api.domain.spi.IBrandPersistencePort;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;


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

    @Override
    public PagedResult<Brand> getAllBrands(Pagination pagination, SortCriteria sortCriteria) {
        PagedResult<Brand> brandPage = brandPersistencePort.getAllBrands(pagination, sortCriteria);
        if (brandPage.getTotalElements() == 0) {
            throw new com.emazon.stock.api.domain.exception.PageException("Marcas");
        }
        return brandPage;
    }


}





