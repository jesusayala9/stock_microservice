package com.emazon.stock.api.application.mapper;

import com.emazon.stock.api.application.dto.ProductResponse;
import com.emazon.stock.api.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {CategoryResponseMapper.class})
public interface ProductResponseMapper {
    @Mapping(source = "categoryIds", target = "categoryIds")

    ProductResponse productToProductResponse(Product product);
}
