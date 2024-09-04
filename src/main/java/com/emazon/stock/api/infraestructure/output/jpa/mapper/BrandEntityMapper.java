package com.emazon.stock.api.infraestructure.output.jpa.mapper;

import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.infraestructure.output.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BrandEntityMapper {

    BrandEntity toEntity(Brand brand);
    Brand toBrand(BrandEntity brandEntity);

    @Named("idToBrand")
    default BrandEntity idToBrand(Long brandId) {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brandId);
        return brandEntity;
    }
}
