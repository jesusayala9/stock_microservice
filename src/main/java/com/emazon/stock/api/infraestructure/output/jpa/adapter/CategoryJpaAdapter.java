package com.emazon.stock.api.infraestructure.output.jpa.adapter;

import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;


import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.infraestructure.output.jpa.entity.CategoryEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.ICategoryRepository;
import com.emazon.stock.api.infraestructure.output.jpa.utils.SortConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;


    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }
    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public PagedResult<Category> getAllCategories(Pagination pagination, SortCriteria sortCriteria) {
        Sort.Direction springDirection = SortConverter.convert(sortCriteria.getDirection());
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize(),
                Sort.by(springDirection, sortCriteria.getSortBy()));
        Page<CategoryEntity> categoryEntities = categoryRepository.findAll(pageable);
        List<Category> categories = categoryEntities.map(categoryEntityMapper::toCategory).toList();
        return new PagedResult<>(categories, (int) categoryEntities.getTotalElements(), categoryEntities.getTotalPages());
    }

}



