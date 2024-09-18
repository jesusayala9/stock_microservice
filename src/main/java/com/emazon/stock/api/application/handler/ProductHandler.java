package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.ProductRequest;
import com.emazon.stock.api.application.dto.ProductResponse;
import com.emazon.stock.api.application.mapper.ProductRequestMapper;
import com.emazon.stock.api.domain.api.IProductServicePort;
import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductHandler implements IProductHandler {
    private final IProductServicePort productServicePort;
    private final ProductRequestMapper productRequestMapper;

    @Override
    public void saveProduct(ProductRequest productRequest) {
        Product product = productRequestMapper.toProduct(productRequest);
        productServicePort.saveProduct(product);
    }

    @Override
    public PagedResult<ProductResponse> getAllProducts(Pagination pagination, SortCriteria sortCriteria) {
        PagedResult<Product> pagedProducts = productServicePort.getAllProducts(
                pagination,
                sortCriteria
        );
        List<ProductResponse> productResponses = pagedProducts.getContent().stream()
                .map(product -> new ProductResponse(
                        product.getName(),
                        product.getDescription(),
                        product.getQuantity(),
                        product.getPrice(),
                        product.getBrandId(),
                        product.getCategoryIds()
                ))
                .toList();
        return new PagedResult<>(productResponses, pagedProducts.getTotalElements(), pagedProducts.getTotalPages());
    }
}


