package com.emazon.stock.api.infraestructure.output.jpa.adapter;

import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.spi.IProductPersistencePort;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.infraestructure.output.jpa.entity.ProductEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;
    @Override
    public void saveProduct(Product product) {
        productRepository.save(productEntityMapper.toEntity(product));

    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public PagedResult<Product> getAllProducts(Pagination pagination, SortCriteria sortCriteria) {
        // Configurar la ordenación
        Sort sort = Sort.by(Sort.Direction.fromString(sortCriteria.getDirection().name()), sortCriteria.getSortBy());

        // Crear la solicitud de página
        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getSize(), sort);

        // Obtener los productos paginados y ordenados desde el repositorio
        Page<ProductEntity> productEntityPage = productRepository.findAll(pageRequest);

        // Mapear los ProductEntity a Product
        List<Product> products = productEntityPage.getContent().stream()
                .map(productEntityMapper::toProduct)
                .toList();

        // Crear y devolver el resultado paginado
        return new PagedResult<>(products, (int) productEntityPage.getTotalElements(), pagination.getSize());
    }
}



