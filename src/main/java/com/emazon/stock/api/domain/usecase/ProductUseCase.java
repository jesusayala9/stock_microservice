package com.emazon.stock.api.domain.usecase;

import com.emazon.stock.api.domain.api.IProductServicePort;

import com.emazon.stock.api.domain.exception.EntityAlreadyExistsException;

import com.emazon.stock.api.domain.model.Product;

import com.emazon.stock.api.domain.spi.IProductPersistencePort;
import com.emazon.stock.api.domain.utils.ProductConstants;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;

public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;


    public ProductUseCase(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public void saveProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException(ProductConstants.EMPTY_NAME_MESSAGE.getMessage());
        }
        if(productPersistencePort.existsByName(product.getName())){
            throw new EntityAlreadyExistsException("Producto");
        }
        productPersistencePort.saveProduct(product);
    }

    @Override
    public PagedResult<Product> getAllProducts(Pagination pagination, SortCriteria sortCriteria) {
        return productPersistencePort.getAllProducts(pagination, sortCriteria);
    }
}
