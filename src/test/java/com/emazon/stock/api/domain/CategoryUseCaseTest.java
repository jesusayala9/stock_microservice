package com.emazon.stock.api.domain;
import com.emazon.stock.api.domain.exception.EntityAlreadyExistsException;
import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;
import com.emazon.stock.api.domain.usecase.CategoryUseCase;
import org.junit.jupiter.api.Test;
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

}
