package com.emazon.stock.api.infraestructure.configuration;

import com.emazon.stock.api.domain.api.ICategoryServicePort;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;
import com.emazon.stock.api.domain.usecase.CategoryUseCase;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.CategoryJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }
}
