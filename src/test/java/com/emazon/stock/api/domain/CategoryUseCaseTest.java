package com.emazon.stock.api.domain;
import com.emazon.stock.api.domain.exception.EntityAlreadyExistsException;
import com.emazon.stock.api.domain.exception.PageException;
import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;
import com.emazon.stock.api.domain.usecase.CategoryUseCase;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.domain.utils.pagination.SortDirection;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    private final ICategoryPersistencePort categoryPersistencePortMock = mock(ICategoryPersistencePort.class, "categoryPersistencePort");

    @Test
    void saveCategoryAlreadyExistsException() {

        when(categoryPersistencePortMock.existsByName("Electronics")).thenReturn(true);
        CategoryUseCase target = new CategoryUseCase(categoryPersistencePortMock);
        Category category = new Category();
        category.setName("Electronics");
        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            target.saveCategory(category);
        });
        assertNotNull(exception);
        assertEquals("Categoria  Ya existe", exception.getMessage());
        verify(categoryPersistencePortMock).existsByName("Electronics");
        verify(categoryPersistencePortMock, never()).saveCategory(category);
    }

    @Test
    void saveCategorySuccessfully() {
        when(categoryPersistencePortMock.existsByName("Electronics")).thenReturn(false);
        CategoryUseCase target = new CategoryUseCase(categoryPersistencePortMock);
        Category category = new Category();
        category.setName("Electronics");
        target.saveCategory(category);
        verify(categoryPersistencePortMock).existsByName("Electronics");
        verify(categoryPersistencePortMock).saveCategory(category);
    }

    @Test
    void getAllCategories() {

        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        List<Category> categories = List.of(new Category(1L, "Electronics", "Electronics"), new Category(2L, "Books", "Books"));
        PagedResult<Category> pagedResult = new PagedResult<>(categories, categories.size(), pagination.getSize());
        CategoryUseCase categoryUseCase = new CategoryUseCase(categoryPersistencePortMock);
        when(categoryPersistencePortMock.getAllCategories(pagination, sortCriteria)).thenReturn(pagedResult);
        PagedResult<Category> result = categoryUseCase.getAllCategories(pagination, sortCriteria);
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        verify(categoryPersistencePortMock, times(1)).getAllCategories(pagination, sortCriteria);
    }

    @Test
    void getAllCategoriesException() {
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        PagedResult<Category> emptyPagedResult = new PagedResult<>(Collections.emptyList(), 0, pagination.getSize());
        CategoryUseCase categoryUseCase = new CategoryUseCase(categoryPersistencePortMock);
        when(categoryPersistencePortMock.getAllCategories(pagination, sortCriteria)).thenReturn(emptyPagedResult);
        assertThrows(PageException.class, () -> {
            categoryUseCase.getAllCategories(pagination, sortCriteria);
        });
        verify(categoryPersistencePortMock, times(1)).getAllCategories(pagination, sortCriteria);
    }
}
