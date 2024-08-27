package com.emazon.stock.api.domain.usecase;
import com.emazon.stock.api.infraestructure.exception.GlobalCategoryException;
import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;
import com.emazon.stock.api.domain.utils.PagedResult;
import com.emazon.stock.api.domain.utils.Pagination;
import com.emazon.stock.api.domain.utils.SortCriteria;
import com.emazon.stock.api.domain.utils.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


import static org.mockito.Mockito.*;

class TestGetCategoryUseCase {
    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategoriesTest() {
        // Arrange
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Electronics", "Electronic items"));
        PagedResult<Category> pagedResult = new PagedResult<>(categories, categories.size(), 1);

        when(categoryPersistencePort.getAllCategories(pagination, sortCriteria)).thenReturn(pagedResult);

        // Act
        PagedResult<Category> result = categoryUseCase.getAllCategories(pagination, sortCriteria);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals("Electronics", result.getContent().get(0).getName());
        verify(categoryPersistencePort, times(1)).getAllCategories(pagination, sortCriteria);
    }

    @Test
    void getAllCategoriesEmptyTest() {
        // Arrange
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        PagedResult<Category> pagedResult = new PagedResult<>(new ArrayList<>(), 0, 0);

        when(categoryPersistencePort.getAllCategories(pagination, sortCriteria)).thenReturn(pagedResult);

        // Act & Assert
        GlobalCategoryException exception = assertThrows(GlobalCategoryException.class, () -> {
            categoryUseCase.getAllCategories(pagination, sortCriteria);
        });

        assertEquals("No hay categorias", exception.getMessage());
        verify(categoryPersistencePort, times(1)).getAllCategories(pagination, sortCriteria);
    }



}

