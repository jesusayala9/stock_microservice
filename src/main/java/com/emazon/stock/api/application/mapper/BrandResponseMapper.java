package com.emazon.stock.api.application.mapper;
import com.emazon.stock.api.application.dto.BrandResponse;
import com.emazon.stock.api.domain.model.Brand;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper (componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BrandResponseMapper {

    BrandResponse toResponse(Brand brand);
    List<BrandResponse> toResponseList(List<Brand> brands);
}
