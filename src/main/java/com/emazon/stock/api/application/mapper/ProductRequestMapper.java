package com.emazon.stock.api.application.mapper;

import com.emazon.stock.api.application.dto.ProductRequest;
import com.emazon.stock.api.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ProductRequestMapper {
    Product toProduct(ProductRequest productRequest);
}
