package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.ProductRequest;
import com.emazon.stock.api.application.dto.ProductResponse;
import com.emazon.stock.api.application.mapper.ProductRequestMapper;
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


@Service
@RequiredArgsConstructor
@Transactional
public class ProductHandler implements IProductHandler{
    private final IProductServicePort productServicePort;
    private final ProductRequestMapper productRequestMapper;

    @Override
    public void saveProduct(ProductRequest productRequest) {
        Product product = productRequestMapper.toProduct(productRequest);
        productServicePort.saveProduct(product);
    }
    @Override
    public PagedResult<ProductResponse> getAllProducts(
            int page, int size, String sortBy, String direction,
            String name, String brand, String categories) {

        Pagination pagination = new Pagination(page, size);
        SortDirection sortDirection = getSortDirection(direction);
        SortCriteria sortCriteria = new SortCriteria(sortBy, sortDirection);
        PagedResult<Product> pagedProducts = productServicePort.getAllProducts(
                pagination, sortCriteria, name, brand, categories);
        List<ProductResponse> productResponses = pagedProducts.getContent().stream()
                .map(product -> new ProductResponse(
                        product.getName(),
                        product.getDescription(),
                        product.getQuantity(),
                        product.getPrice(),
                        product.getBrandId(),
                        product.getCategoryIds()
                )).toList();
        return new PagedResult<>(productResponses, pagedProducts.getTotalElements(), pagedProducts.getTotalPages());
    }

    private SortDirection getSortDirection(String direction) {
        if (direction == null) {
            return SortDirection.ASC;
        }
        for (SortDirection sortDirection : SortDirection.values()) {
            if (sortDirection.name().equalsIgnoreCase(direction)) {
                return sortDirection;
            }
        }
        return SortDirection.ASC;
    }


}
