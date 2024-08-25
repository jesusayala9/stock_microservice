package com.emazon.stock.api.infraestructure.configuration;

import com.emazon.stock.api.domain.api.IBrandServicePort;
import com.emazon.stock.api.domain.api.ICategoryServicePort;
import com.emazon.stock.api.domain.spi.IBrandPersistencePort;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;
import com.emazon.stock.api.domain.usecase.BrandUseCase;
import com.emazon.stock.api.domain.usecase.CategoryUseCase;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.BrandJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.CategoryJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.BrandEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IBrandRepository;
import com.emazon.stock.api.infraestructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    private final IBrandRepository brandRepository;

    private final BrandEntityMapper brandEntityMapper;



    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }


    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort() {
        return new BrandUseCase(brandPersistencePort());
    }


}
