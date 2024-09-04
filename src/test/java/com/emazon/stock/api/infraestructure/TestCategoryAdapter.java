package com.emazon.stock.api.infraestructure;
import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.domain.utils.pagination.SortDirection;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.CategoryJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.entity.CategoryEntity;
import com.emazon.stock.api.infraestructure.output.jpa.entity.ProductEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

class TestCategoryAdapter {
    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private CategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCategory_ShouldSaveCategorySuccessfully() {
        Category category = new Category(1L, "Electronics", "All electronic items");

        // Usar el constructor sin argumentos
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Electronics");
        categoryEntity.setDescription("All electronic items");
        categoryEntity.setProducts(new HashSet<>());  // Asignar un conjunto vacío de productos

        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);

        categoryJpaAdapter.saveCategory(category);

        verify(categoryRepository).save(categoryEntity);
    }

    @Test
    void TestIfCategoryExists() {
        String categoryName = "Electronics";
        when(categoryRepository.existsByName(categoryName)).thenReturn(true);
        boolean exists = categoryJpaAdapter.existsByName(categoryName);
        verify(categoryRepository).existsByName(categoryName);
        assertTrue("true", exists);
    }

    @Test
    void TestIfCategoryNotExists() {
        String categoryName = "CategoryName";
        when(categoryRepository.existsByName(categoryName)).thenReturn(false);
        boolean exists = categoryJpaAdapter.existsByName(categoryName);
        verify(categoryRepository).existsByName(categoryName);
        assertFalse("false", exists);
    }

    @Test
    void getAllCategoriesWhenExist() {
        Pagination pagination = new Pagination(0, 1); // Page size 1
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);

        // Set vacío para productos
        Set<ProductEntity> products = new HashSet<>();

        // Crear una CategoryEntity con el Set de productos
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "All electronic items", products);

        List<CategoryEntity> categoryEntityList = List.of(categoryEntity);
        Page<CategoryEntity> categoryEntityPage = new PageImpl<>(categoryEntityList);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryEntityPage);
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(new Category(1L, "Electronics", "All electronic items"));

        PagedResult<Category> pagedResult = categoryJpaAdapter.getAllCategories(pagination, sortCriteria);

        assertEquals(1, pagedResult.getTotalPages(), "Expected total pages to be 1.");
        assertEquals(1, pagedResult.getTotalElements(), "Expected total elements to be 1.");
    }

    @Test
    void getAllCategoriesWhenNotExist() {
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);

        List<CategoryEntity> categoryEntityList = Collections.emptyList();
        Page<CategoryEntity> categoryEntityPage = new PageImpl<>(categoryEntityList);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryEntityPage);

        PagedResult<Category> pagedResult = categoryJpaAdapter.getAllCategories(pagination, sortCriteria);

        assertEquals(0, pagedResult.getTotalPages(), "Expected total pages to be 0.");
        assertEquals(0, pagedResult.getTotalElements(), "Expected total elements to be 0.");
    }



}