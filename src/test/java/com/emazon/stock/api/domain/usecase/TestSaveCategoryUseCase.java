package com.emazon.stock.api.domain.usecase;
import com.emazon.stock.api.infraestructure.exception.GlobalCategoryException;
import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TestSaveCategoryUseCase {
    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCategoryTest() {
        Category category = new Category(1L, "Electronica", "Productos electronicos");
        categoryUseCase.saveCategory(category);
        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    void ExceptionIfNameIsEmpty() {
        Category category = new Category(1L, "", "Descripción válida");
        GlobalCategoryException exception = assertThrows(GlobalCategoryException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
        assertEquals("Nombre no puede ser vacio", exception.getMessage());
        verify(categoryPersistencePort, never()).saveCategory(category);
    }
    @Test
    void ExceptionIfNameIsTooLong() {
        String longName = "Nombre extremadamente largo que excede los 50 caracteres permitidos";
        Category category = new Category(1L, longName, "Descripción válida");
        GlobalCategoryException exception = assertThrows(GlobalCategoryException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
        assertEquals("El nombre es muy largo", exception.getMessage());
        verify(categoryPersistencePort, never()).saveCategory(category);
    }
    @Test
    void ExceptionIfDescriptionIsEmpty() {
        Category category = new Category(1L, "Nombre válido", "");
        GlobalCategoryException exception = assertThrows(GlobalCategoryException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
        assertEquals("Descripcion no puede ser vacio", exception.getMessage());
        verify(categoryPersistencePort, never()).saveCategory(category);
    }
    @Test
    void ExceptionIfDescriptionIsTooLong() {
        String longDescription = "Descripción extremadamente larga que excede los 90 caracteres permitidos por el sistema para esta entidad.";
        Category category = new Category(1L, "Nombre válido", longDescription);
        GlobalCategoryException exception = assertThrows(GlobalCategoryException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
        assertEquals("Descripcion es muy larga", exception.getMessage());
        verify(categoryPersistencePort, never()).saveCategory(category);
    }
    @Test
    void ExceptionIfCategoryAlreadyExists() {
        Category category = new Category(1L, "Electronica", "Productos electronicos");
        when(categoryPersistencePort.existsByName("Electronica")).thenReturn(true);
        GlobalCategoryException exception = assertThrows(GlobalCategoryException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
        assertEquals("Categoria ya existe", exception.getMessage());
        verify(categoryPersistencePort, never()).saveCategory(category);
    }

}
