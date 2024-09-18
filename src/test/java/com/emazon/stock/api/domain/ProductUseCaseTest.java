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

import java.util.ArrayList;
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

        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "Laptop", "High performance laptop", 10, 1500.0, 1L, List.of(1L, 2L)));
        PagedResult<Product> pagedResult = new PagedResult<>(productList, productList.size(), 1);

        when(productPersistencePortMock.getAllProducts(pagination, sortCriteria)).thenReturn(pagedResult);

        PagedResult<Product> result = productUseCase.getAllProducts(pagination, sortCriteria);

        assertNotNull(result);
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getContent().size());
        assertEquals("Laptop", result.getContent().get(0).getName());

        verify(productPersistencePortMock).getAllProducts(pagination, sortCriteria);
    }
    @Test
    void getAllProductsThrowsPageException() {
        // Arrange
        Pagination pagination = new Pagination(2, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);

        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "Laptop", "High performance laptop", 10, 1500.0, 1L, List.of(1L, 2L)));
        PagedResult<Product> pagedResult = new PagedResult<>(productList, productList.size(), 1);

        when(productPersistencePortMock.getAllProducts(pagination, sortCriteria)).thenReturn(pagedResult);

        // Act & Assert
        PageException exception = assertThrows(PageException.class, () -> {
            productUseCase.getAllProducts(pagination, sortCriteria);
        });

        assertNotNull(exception);
        assertEquals("No hay Producto", exception.getMessage());

        verify(productPersistencePortMock).getAllProducts(pagination, sortCriteria);
    }

    @Test
    void getAllProductsWhenEmpty() {
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        List<Product> emptyProductList = new ArrayList<>();
        PagedResult<Product> pagedResult = new PagedResult<>(emptyProductList, 0, 0);
        when(productPersistencePortMock.getAllProducts(pagination, sortCriteria)).thenReturn(pagedResult);
        PageException exception = assertThrows(PageException.class, () -> {
            productUseCase.getAllProducts(pagination, sortCriteria);
        });
        assertEquals("No hay Producto", exception.getMessage());
        verify(productPersistencePortMock).getAllProducts(pagination, sortCriteria);
    }

}
