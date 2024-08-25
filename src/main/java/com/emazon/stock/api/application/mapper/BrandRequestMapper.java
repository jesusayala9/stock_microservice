package com.emazon.stock.api.application.mapper;

import com.emazon.stock.api.application.dto.BrandRequest;

import com.emazon.stock.api.domain.model.Brand;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface BrandRequestMapper {

    Brand toBrand(BrandRequest brandRequest);
}
