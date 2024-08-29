package com.emazon.stock.api.infraestructure;

import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.CategoryJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.entity.CategoryEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "All electronic items");
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



}
