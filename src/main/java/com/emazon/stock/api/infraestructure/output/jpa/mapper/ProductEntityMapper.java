package com.emazon.stock.api.infraestructure.output.jpa.mapper;

import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.infraestructure.output.jpa.entity.CategoryEntity;
import com.emazon.stock.api.infraestructure.output.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {BrandEntityMapper.class, CategoryEntityMapper.class})
public interface ProductEntityMapper {
    @Mapping(source = "brandId", target = "brand", qualifiedByName = "idToBrand")
    @Mapping(source = "categoryIds", target = "categories", qualifiedByName = "idToCategory")
    ProductEntity toEntity(Product product);

    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "categories", target = "categoryIds", qualifiedByName = "categoryEntitySetToLongList")
    Product toProduct(ProductEntity productEntity);

    @Named("idToCategory")
    default Set<CategoryEntity> idToCategory(List<Long> categoryIds) {
        return categoryIds.stream().map(id -> {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(id);
            return categoryEntity;
        }).collect(Collectors.toSet());
    }


    @Named("categoryEntitySetToLongList")
    default List<Long> categoryEntitySetToLongList(Set<CategoryEntity> categories) {
        return categories.stream().map(CategoryEntity::getId).toList();
    }

    List<Product> toProductList(List<ProductEntity> productEntities);
}