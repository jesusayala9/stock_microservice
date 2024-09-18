package com.emazon.stock.api.infraestructure.output.jpa.adapter;
import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.spi.IProductPersistencePort;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.infraestructure.output.jpa.entity.ProductEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public void saveProduct(Product product) {
        productRepository.save(productEntityMapper.toEntity(product));

    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public PagedResult<Product> getAllProducts(Pagination pagination, SortCriteria sortCriteria) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize(),
                Sort.by(Sort.Direction.fromString(sortCriteria.getDirection().name()), mapSortBy(sortCriteria.getSortBy())));

        Page<ProductEntity> productPage = productRepository.findAll(pageable);
        List<Product> products = productEntityMapper.toProductList(productPage.getContent());

        return new PagedResult<>(products, (int) productPage.getTotalElements(), pagination.getSize());
    }

    private String mapSortBy(String sortBy) {
        switch (sortBy) {
            case "brandName":
                return "brand.name";
            case "categoryName":
                return "categories.name";
            default:
                return "name";
        }
    }

}



