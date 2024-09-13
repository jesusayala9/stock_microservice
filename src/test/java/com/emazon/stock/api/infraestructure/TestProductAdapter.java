package com.emazon.stock.api.infraestructure;

import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.domain.utils.pagination.SortDirection;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.ProductJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.entity.ProductEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TestProductAdapter {

    @Mock
    private IProductRepository productRepositoryMock;

    @Mock
    private ProductEntityMapper productEntityMapperMock;

    private ProductJpaAdapter productJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productJpaAdapter = new ProductJpaAdapter(productRepositoryMock, productEntityMapperMock);
    }

    @Test
    void saveProductTest() {
        // Arrange
        Product productMock = mock(Product.class);
        ProductEntity productEntityMock = mock(ProductEntity.class);

        when(productEntityMapperMock.toEntity(productMock)).thenReturn(productEntityMock);
        when(productRepositoryMock.save(productEntityMock)).thenReturn(productEntityMock);

        // Act
        productJpaAdapter.saveProduct(productMock);

        // Assert
        verify(productEntityMapperMock).toEntity(productMock);
        verify(productRepositoryMock).save(productEntityMock);
    }

    @Test
    void existsByNameTest() {
        // Arrange
        String productName = "Laptop";
        when(productRepositoryMock.existsByName(productName)).thenReturn(true);

        // Act
        boolean result = productJpaAdapter.existsByName(productName);

        // Assert
        assertTrue(result);
        verify(productRepositoryMock).existsByName(productName);
    }

    @Test
    void getAllProductsTest() {
        // Arrange
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        List<ProductEntity> productEntities = Collections.singletonList(new ProductEntity());
        Page<ProductEntity> pageResultMock = mock(Page.class);

        when(pageResultMock.getContent()).thenReturn(productEntities);
        when(pageResultMock.getTotalElements()).thenReturn(1L);
        when(pageResultMock.getSize()).thenReturn(10);
        when(productRepositoryMock.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(pageResultMock);

        Product productMock = new Product();
        when(productEntityMapperMock.toProduct(any(ProductEntity.class))).thenReturn(productMock);

        // Act
        PagedResult<Product> result = productJpaAdapter.getAllProducts(pagination, sortCriteria, null, null, null);

        // Assert
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        verify(productRepositoryMock).findAll(any(Specification.class), any(PageRequest.class));
        verify(productEntityMapperMock).toProduct(any(ProductEntity.class));
    }

    @Test
    void getAllProductsWithNameFilterTest() {
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        String name = "Product Name";

        ProductEntity productEntityMock = new ProductEntity();
        List<ProductEntity> productEntities = Collections.singletonList(productEntityMock);
        Page<ProductEntity> pageResultMock = mock(Page.class);

        when(pageResultMock.getContent()).thenReturn(productEntities);
        when(pageResultMock.getTotalElements()).thenReturn(1L);
        when(pageResultMock.getSize()).thenReturn(10);
        when(productRepositoryMock.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(pageResultMock);

        Product productMock = new Product();
        when(productEntityMapperMock.toProduct(productEntityMock)).thenReturn(productMock);

        productJpaAdapter.getAllProducts(pagination, sortCriteria, name, null, null);

        verify(productRepositoryMock).findAll(any(Specification.class), any(PageRequest.class));
        verify(productEntityMapperMock).toProduct(productEntityMock);
    }
}
