package com.emazon.stock.api.infraestructure;

import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.ProductJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.entity.ProductEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
}
