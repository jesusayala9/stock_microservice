package com.emazon.stock.api.domain;
import com.emazon.stock.api.domain.exception.EntityAlreadyExistsException;
import com.emazon.stock.api.domain.exception.PageException;
import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;
import com.emazon.stock.api.domain.usecase.CategoryUseCase;
import com.emazon.stock.api.domain.utils.CategoryConstants;
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

class CategoryUseCaseTest {



    @Mock
    private ICategoryPersistencePort categoryPersistencePortMock;

    private CategoryUseCase categoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryUseCase = new CategoryUseCase(categoryPersistencePortMock);
    }

    @Test
    void saveCategoryAlreadyExistsException() {
        // Arrange
        when(categoryPersistencePortMock.existsByName("Electronics")).thenReturn(true);
        Category category = new Category();
        category.setName("Electronics");

        // Act & Assert
        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        assertNotNull(exception);
        assertEquals("Categoria  Ya existe", exception.getMessage());

        verify(categoryPersistencePortMock).existsByName("Electronics");
        verify(categoryPersistencePortMock, never()).saveCategory(any(Category.class));
    }

    @Test
    void saveCategorySuccessfully() {
        // Arrange
        when(categoryPersistencePortMock.existsByName("Electronics")).thenReturn(false);
        Category category = new Category();
        category.setName("Electronics");

        // Act
        CategoryUseCase target = new CategoryUseCase(categoryPersistencePortMock);
        target.saveCategory(category);

        // Assert
        verify(categoryPersistencePortMock).existsByName("Electronics");
        verify(categoryPersistencePortMock).saveCategory(category);
    }

    @Test
    void saveCategoryWhenCategoryIsNull() {
        CategoryUseCase target = new CategoryUseCase(categoryPersistencePortMock);
        assertThrows(NullPointerException.class, () -> {
            target.saveCategory(null);
        });
    }

    @Test
    void saveCategoryWithEmptyName() {
        // Arrange
        CategoryUseCase target = new CategoryUseCase(categoryPersistencePortMock);
        Category category = new Category();
        category.setName(""); // Set name to empty

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            target.saveCategory(category);
        });

        // Assert
        assertEquals(CategoryConstants.EMPTY_NAME_MESSAGE.getMessage(), exception.getMessage());
    }

    @Test
    void getAllCategoriesWhenCategoriesExist() {
        // Arrange
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        List<Category> categories = List.of(
                new Category(1L, "Electronics", "Electronics"),
                new Category(2L, "Books", "Books")
        );
        PagedResult<Category> pagedResult = new PagedResult<>(categories, categories.size(), pagination.getSize());

        when(categoryPersistencePortMock.getAllCategories(pagination, sortCriteria)).thenReturn(pagedResult);


        categoryUseCase = new CategoryUseCase(categoryPersistencePortMock);
        PagedResult<Category> result = categoryUseCase.getAllCategories(pagination, sortCriteria);


        assertNotNull(result);
        assertEquals(2, result.getTotalElements()); // Asegúrate de que este valor sea correcto
        assertEquals(1, result.getTotalPages()); // También verifica este valor
        verify(categoryPersistencePortMock, times(1)).getAllCategories(pagination, sortCriteria);
    }

    @Test
    void getAllCategoriesWhenNoCategoriesExist() {
        // Arrange
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);

        PagedResult<Category> emptyPagedResult = new PagedResult<>(Collections.emptyList(), 0, 10);
        when(categoryPersistencePortMock.getAllCategories(pagination, sortCriteria)).thenReturn(emptyPagedResult);

        // Act & Assert
        PageException exception = assertThrows(PageException.class, () -> {
            categoryUseCase.getAllCategories(pagination, sortCriteria);
        });

        assertNotNull(exception);
        assertEquals("No hay Categorias", exception.getMessage());

        verify(categoryPersistencePortMock, times(1)).getAllCategories(pagination, sortCriteria);
    }




}
