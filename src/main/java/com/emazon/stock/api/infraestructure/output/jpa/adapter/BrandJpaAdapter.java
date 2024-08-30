package com.emazon.stock.api.infraestructure.output.jpa.adapter;
import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.IBrandPersistencePort;

import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.infraestructure.output.jpa.entity.BrandEntity;
import com.emazon.stock.api.infraestructure.output.jpa.entity.CategoryEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.BrandEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IBrandRepository;

import com.emazon.stock.api.infraestructure.output.jpa.utils.SortConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


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

    @Override
    public PagedResult<Brand> getAllBrands(Pagination pagination, SortCriteria sortCriteria) {
        Sort.Direction springDirection = SortConverter.convert(sortCriteria.getDirection());
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize(),
                Sort.by(springDirection, sortCriteria.getSortBy()));
        Page<BrandEntity> brandEntities = brandRepository.findAll(pageable);
        List<Brand> brands = brandEntities.map(brandEntityMapper::toBrand).toList();
        return new PagedResult<>(brands, (int) brandEntities.getTotalElements(), brandEntities.getTotalPages());
    }


}
