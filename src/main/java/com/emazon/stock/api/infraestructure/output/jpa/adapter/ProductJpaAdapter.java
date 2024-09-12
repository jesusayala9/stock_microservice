package com.emazon.stock.api.infraestructure.output.jpa.adapter;

import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.spi.IProductPersistencePort;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.infraestructure.output.jpa.entity.ProductEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IProductRepository;
import com.emazon.stock.api.infraestructure.output.jpa.utils.ProductSpecifications;
import com.emazon.stock.api.infraestructure.output.jpa.utils.SortConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

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
    public PagedResult<Product> getAllProducts(
            Pagination pagination,
            SortCriteria sortCriteria,
            String name,
            String brand,
            String categories
    ) {
        Sort.Direction sortDirection = SortConverter.convert(sortCriteria.getDirection());
        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getSize(), Sort.by(sortDirection, sortCriteria.getSortBy()));

        Specification<ProductEntity> spec = Specification.where(null);
        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecifications.hasName(name));
        }
        if (brand != null && !brand.isEmpty()) {
            spec = spec.and(ProductSpecifications.hasBrandName(brand));
        }
        if (categories != null && !categories.isEmpty()) {
            spec = spec.and(ProductSpecifications.hasCategoryName(categories));
        }

        Page<ProductEntity> pageResult = productRepository.findAll(spec, pageRequest);
        List<Product> products = pageResult.getContent().stream()
                .map(productEntityMapper::toProduct)
                .toList();
        int totalElements = (int) Math.min(pageResult.getTotalElements(), Integer.MAX_VALUE);
        return new PagedResult<>(products, totalElements, pageResult.getSize());
    }
}



