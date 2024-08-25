package com.emazon.stock.api.infraestructure.output.jpa.adapter;

import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.domain.spi.IBrandPersistencePort;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.BrandEntityMapper;

import com.emazon.stock.api.infraestructure.output.jpa.repository.IBrandRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;
    @Override
    public void saveBrand(Brand brand) {
        brandRepository.save(brandEntityMapper.toEntity(brand));
    }

    @Override
    public boolean existsByName(String name) {
        return brandRepository.existsByName(name);
    }
}
