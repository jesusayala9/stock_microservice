package com.emazon.stock.api.infraestructure.output.jpa.adapter;

import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;

import com.emazon.stock.api.infraestructure.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;



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


}
