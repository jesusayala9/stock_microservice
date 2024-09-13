package com.emazon.stock.api.domain;
import com.emazon.stock.api.domain.exception.EntityAlreadyExistsException;
import com.emazon.stock.api.domain.exception.PageException;
import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.spi.IProductPersistencePort;
import com.emazon.stock.api.domain.usecase.ProductUseCase;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.domain.utils.pagination.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
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

    @Test
    void getAllProductsSuccessfully() {
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        List<Product> products = Collections.nCopies(10, new Product());
        PagedResult<Product> pagedResultMock = new PagedResult<>(products, 10, 10);
        when(productPersistencePortMock.getAllProducts(pagination, sortCriteria, "name", "brand", "category"))
                .thenReturn(pagedResultMock);
        // Act
        PagedResult<Product> result = productUseCase.getAllProducts(pagination, sortCriteria, "name", "brand", "category");
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalPages());
        assertEquals(10, result.getTotalElements());
        assertEquals(10, result.getContent().size());
        verify(productPersistencePortMock).getAllProducts(pagination, sortCriteria, "name", "brand", "category");
    }

    @Test
    void getAllProductsPageExceptionWhenPageIsNegative() {
        // Arrange
        Pagination pagination = new Pagination(-1, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);

        PagedResult<Product> pagedResultMock = new PagedResult<>(Collections.emptyList(), 1, 1);
        when(productPersistencePortMock.getAllProducts(pagination, sortCriteria, null, null, null))
                .thenReturn(pagedResultMock);
        // Act & Assert
        PageException exception = assertThrows(PageException.class, () -> {
            productUseCase.getAllProducts(pagination, sortCriteria, null, null, null);
        });

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Producto"));
        verify(productPersistencePortMock, times(1)).getAllProducts(pagination, sortCriteria, null, null, null);
    }


}
