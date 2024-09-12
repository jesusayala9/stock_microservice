package com.emazon.stock.api.domain;
import com.emazon.stock.api.domain.exception.EntityAlreadyExistsException;
import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.spi.IProductPersistencePort;
import com.emazon.stock.api.domain.usecase.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductUseCaseTest {

    @Mock
    private IProductPersistencePort productPersistencePortMock;

    private ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productUseCase = new ProductUseCase(productPersistencePortMock);
    }

    @Test
    void saveProductAlreadyExistsException() {
        // Arrange
        when(productPersistencePortMock.existsByName("Laptop")).thenReturn(true);
        Product product = new Product();
        product.setName("Laptop");

        // Act & Assert
        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            productUseCase.saveProduct(product);
        });

        assertNotNull(exception);
        assertEquals("Producto ya existe", exception.getMessage());

        verify(productPersistencePortMock).existsByName("Laptop");
        verify(productPersistencePortMock, never()).saveProduct(any(Product.class));
    }

    @Test
    void saveProductSuccessfully() {
        // Arrange
        when(productPersistencePortMock.existsByName("Laptop")).thenReturn(false);
        Product product = new Product();
        product.setName("Laptop");
        product.setDescription("High performance laptop");
        product.setQuantity(10);
        product.setPrice(1500.0);
        product.setBrandId(1L);
        product.setCategoryIds(List.of(1L, 2L));

        // Act
        productUseCase.saveProduct(product);

        // Assert
        verify(productPersistencePortMock).existsByName("Laptop");
        verify(productPersistencePortMock).saveProduct(product);
    }

    @Test
    void saveProductWhenProductIsNull() {
        assertThrows(NullPointerException.class, () -> {
            productUseCase.saveProduct(null);
        });
    }

}
