package com.emazon.stock.api.infraestructure.output.jpa.utils;

import com.emazon.stock.api.infraestructure.output.jpa.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<ProductEntity> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<ProductEntity> hasBrandName(String brandName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("brand").get("name"), brandName);
    }

    public static Specification<ProductEntity> hasCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("categories").get("name"), categoryName);
    }
}
