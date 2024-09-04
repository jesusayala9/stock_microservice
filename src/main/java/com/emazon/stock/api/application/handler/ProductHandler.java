package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.ProductRequest;
import com.emazon.stock.api.application.mapper.ProductRequestMapper;
import com.emazon.stock.api.domain.api.IProductServicePort;
import com.emazon.stock.api.domain.model.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
