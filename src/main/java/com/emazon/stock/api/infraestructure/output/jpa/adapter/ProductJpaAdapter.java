package com.emazon.stock.api.infraestructure.output.jpa.adapter;

import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.spi.IProductPersistencePort;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;
    @Override
    public void saveProduct(Product product) {
        productRepository.save(productEntityMapper.toEntity(product));

    }
}
