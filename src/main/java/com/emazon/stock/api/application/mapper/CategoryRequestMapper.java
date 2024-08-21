package com.emazon.stock.api.application.mapper;

import com.emazon.stock.api.application.dto.CategoryRequest;
import com.emazon.stock.api.domain.model.Category;

import org.mapstruct.Mapper;

import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryRequestMapper {

    Category toCategory(CategoryRequest categoryRequest);

}
