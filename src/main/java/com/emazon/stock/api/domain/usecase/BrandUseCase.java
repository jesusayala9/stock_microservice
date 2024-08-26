package com.emazon.stock.api.domain.usecase;

import com.emazon.stock.api.domain.api.IBrandServicePort;
import com.emazon.stock.api.domain.exception.GlobalBrandException;
import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.domain.spi.IBrandPersistencePort;
import com.emazon.stock.api.domain.utils.PagedResult;
import com.emazon.stock.api.domain.utils.Pagination;
import com.emazon.stock.api.domain.utils.SortCriteria;

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

    @Override
    public PagedResult<Brand> getAllBrands(Pagination pagination, SortCriteria sortCriteria) {
        if (pagination == null) {
            throw new GlobalBrandException("El parámetro 'pagination' es obligatorio.");
        }

        validateSortCriteria(sortCriteria);

        PagedResult<Brand> brandPage = brandPersistencePort.getAllBrands(pagination, sortCriteria);

        if (brandPage.getTotalElements() == 0) {
            throw new GlobalBrandException("No hay marcas.");
        }

        return brandPage;
    }

    private void validateSortCriteria(SortCriteria sortCriteria) {
        if (sortCriteria == null)   {
            throw new GlobalBrandException("El parámetro 'sortCriteria' es obligatorio.");
        }

        if (sortCriteria.getSortBy() == null || sortCriteria.getSortBy().trim().isEmpty()) {
            throw new GlobalBrandException("El campo 'sortBy' no puede estar vacío.");
        }

        if (sortCriteria.getDirection() == null) {
            throw new GlobalBrandException("El campo 'direction' es obligatorio.");
        }
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
