package com.emazon.stock.api.infraestructure;
import com.emazon.stock.api.domain.model.Product;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.domain.utils.pagination.SortDirection;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.ProductJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.entity.BrandEntity;
import com.emazon.stock.api.infraestructure.output.jpa.entity.CategoryEntity;
import com.emazon.stock.api.infraestructure.output.jpa.entity.ProductEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
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
    void getAllProductsSuccessfully() {
        // Arrange
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize(), Sort.by(Sort.Direction.ASC, "name"));

        Set<CategoryEntity> categoryEntities = new HashSet<>();
        categoryEntities.add(new CategoryEntity(1L, "Electronics"));

        BrandEntity brandEntity = new BrandEntity(1L, "Brand Name");

        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(new ProductEntity(1L, "Laptop", "High performance laptop", 10, 1500.0, brandEntity, categoryEntities));
        Page<ProductEntity> productPage = new PageImpl<>(productEntities, pageable, productEntities.size());

        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Laptop", "High performance laptop", 10, 1500.0, 1L, List.of(1L)));

        when(productRepositoryMock.findAll(pageable)).thenReturn(productPage);
        when(productEntityMapperMock.toProductList(productEntities)).thenReturn(products);

        PagedResult<Product> result = productJpaAdapter.getAllProducts(pagination, sortCriteria);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals("Laptop", result.getContent().get(0).getName());

        verify(productRepositoryMock).findAll(pageable);
        verify(productEntityMapperMock).toProductList(productEntities);
    }

    @Test
    void getAllProducts_emptyPage() {

        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);

        Page<ProductEntity> emptyProductPage = new PageImpl<>(List.of());

        when(productRepositoryMock.findAll(any(Pageable.class))).thenReturn(emptyProductPage);
        when(productEntityMapperMock.toProductList(anyList())).thenReturn(List.of());

        PagedResult<Product> result = productJpaAdapter.getAllProducts(pagination, sortCriteria);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
        assertEquals(10, pagination.getSize());
        verify(productRepositoryMock, times(1)).findAll(any(Pageable.class));
        verify(productEntityMapperMock, times(1)).toProductList(anyList());
    }

    @Test
    void getAllProducts_sortByBrandName() {
        // Arrange
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("brandName", SortDirection.ASC);

        ProductEntity productEntity = new ProductEntity(1L, "Product 1", "Description 1", 10, 100.0, new BrandEntity(), Set.of(new CategoryEntity()));
        Page<ProductEntity> productPage = new PageImpl<>(List.of(productEntity));

        when(productRepositoryMock.findAll(any(Pageable.class))).thenReturn(productPage);
        when(productEntityMapperMock.toProductList(anyList())).thenReturn(List.of(new Product()));

        PagedResult<Product> result = productJpaAdapter.getAllProducts(pagination, sortCriteria);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(10, pagination.getSize());
        verify(productRepositoryMock, times(1)).findAll(any(Pageable.class));
        verify(productEntityMapperMock, times(1)).toProductList(anyList());
    }



    @Test
    void getAllProducts_sortByCategoryName() {

        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("categoryName", SortDirection.DESC);

        ProductEntity productEntity = new ProductEntity(1L, "Product 1", "Description 1", 10, 100.0, new BrandEntity(), Set.of(new CategoryEntity()));
        Page<ProductEntity> productPage = new PageImpl<>(List.of(productEntity));

        when(productRepositoryMock.findAll(any(Pageable.class))).thenReturn(productPage);
        when(productEntityMapperMock.toProductList(anyList())).thenReturn(List.of(new Product()));


        PagedResult<Product> result = productJpaAdapter.getAllProducts(pagination, sortCriteria);


        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(10, pagination.getSize());
        verify(productRepositoryMock, times(1)).findAll(any(Pageable.class));
        verify(productEntityMapperMock, times(1)).toProductList(anyList());
    }

    @Test
    void getAllProducts_sortByName() {

        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);

        ProductEntity productEntity = new ProductEntity(1L, "Product 1", "Description 1", 10, 100.0, new BrandEntity(), Set.of(new CategoryEntity()));
        Page<ProductEntity> productPage = new PageImpl<>(List.of(productEntity));


        when(productRepositoryMock.findAll(any(Pageable.class))).thenReturn(productPage);
        when(productEntityMapperMock.toProductList(anyList())).thenReturn(List.of(new Product()));


        PagedResult<Product> result = productJpaAdapter.getAllProducts(pagination, sortCriteria);


        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(10, pagination.getSize());
        verify(productRepositoryMock, times(1)).findAll(any(Pageable.class));
        verify(productEntityMapperMock, times(1)).toProductList(anyList());
    }




}
