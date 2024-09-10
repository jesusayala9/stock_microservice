package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.ProductRequest;
import com.emazon.stock.api.application.dto.ProductResponse;
import com.emazon.stock.api.application.mapper.ProductRequestMapper;
import com.emazon.stock.api.application.mapper.ProductResponseMapper;
import com.emazon.stock.api.domain.api.IProductServicePort;
import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.domain.utils.pagination.SortDirection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductHandler implements IProductHandler{
    private final IProductServicePort productServicePort;
    private final ProductRequestMapper productRequestMapper;

    private final ProductResponseMapper productResponseMapper;
    @Override
    public void saveProduct(ProductRequest productRequest) {
        Product product = productRequestMapper.toProduct(productRequest);
        productServicePort.saveProduct(product);
    }

    @Override
    public PagedResult<ProductResponse> getAllBrands(int page, int size, String sortBy, String direction) {
        Pagination pagination = new Pagination(page, size);
        SortCriteria sortCriteria = new SortCriteria(sortBy, SortDirection.valueOf(direction.toUpperCase()));

        PagedResult<Product> pagedProducts = productServicePort.getAllProducts(pagination, sortCriteria);

        // Convertir cada Product a ProductResponse usando el mapper
        List<ProductResponse> productResponses = pagedProducts.getContent().stream()
                .map(productResponseMapper::productToProductResponse)
                .collect(Collectors.toList());

        return new PagedResult<>(productResponses, pagedProducts.getTotalElements(), pagedProducts.getTotalPages());
    }

}
