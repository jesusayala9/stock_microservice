package com.emazon.stock.api.infraestructure.configuration;

import com.emazon.stock.api.domain.api.IBrandServicePort;
import com.emazon.stock.api.domain.api.ICategoryServicePort;
import com.emazon.stock.api.domain.api.IProductServicePort;
import com.emazon.stock.api.domain.spi.IBrandPersistencePort;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;
import com.emazon.stock.api.domain.spi.IProductPersistencePort;
import com.emazon.stock.api.domain.usecase.BrandUseCase;
import com.emazon.stock.api.domain.usecase.CategoryUseCase;
import com.emazon.stock.api.domain.usecase.ProductUseCase;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.BrandJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.CategoryJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.ProductJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.BrandEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IBrandRepository;
import com.emazon.stock.api.infraestructure.output.jpa.repository.ICategoryRepository;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IProductRepository;
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

    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;



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



    @Bean
    public IProductPersistencePort productPersistencePort() {
        return new ProductJpaAdapter(productRepository, productEntityMapper);
    }

    @Bean
    public IProductServicePort productServicePort() {
        return new ProductUseCase(productPersistencePort());
    }


}
