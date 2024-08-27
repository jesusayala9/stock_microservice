package com.emazon.stock.api.domain.usecase;

import com.emazon.stock.api.infraestructure.exception.GlobalBrandException;
import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.domain.spi.IBrandPersistencePort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class TestSaveBrandUseCase {
    @InjectMocks
    private BrandUseCase brandUseCase;

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBrandTest() {
        Brand brand = new Brand(1L, "Mazada", "carros japoneses");
        brandUseCase.saveBrand(brand);
        verify(brandPersistencePort, times(1)).saveBrand(brand);
    }

    @Test
    void ExceptionIfNameIsEmpty() {
        Brand brand = new Brand(1L, "", "Descripción válida");
        GlobalBrandException exception = assertThrows(GlobalBrandException.class, () -> {
           brandUseCase.saveBrand(brand);
        });
        assertEquals("Nombre no puede ser vacio", exception.getMessage());
        verify(brandPersistencePort, never()).saveBrand(brand);
    }
    @Test
    void ExceptionIfNameIsTooLong() {
        String longName = "Nombre extremadamente largo que excede los 50 caracteres permitidos";
        Brand brand = new Brand(1L, longName, "Descripción válida");
        GlobalBrandException exception = assertThrows(GlobalBrandException.class, () -> {
            brandUseCase.saveBrand(brand);
        });
        assertEquals("El nombre es muy largo", exception.getMessage());
        verify(brandPersistencePort, never()).saveBrand(brand);
    }
    @Test
    void ExceptionIfDescriptionIsEmpty() {
        Brand brand = new Brand(1L, "Nombre válido", "");
        GlobalBrandException exception = assertThrows(GlobalBrandException.class, () -> {
           brandUseCase.saveBrand(brand);
        });
        assertEquals("Descripcion no puede ser vacio", exception.getMessage());
        verify(brandPersistencePort, never()).saveBrand(brand);
    }
    @Test
    void ExceptionIfDescriptionIsTooLong() {
        String longDescription = "Descripción extremadamente larga que excede los 150 caracteres permitidos por el sistema para esta entidad 12345665656565656565656565656565656565656565656565656565fgdfgdfgdgfdfgdfgd656565656565656565.";
        Brand brand = new Brand(1L, "Nombre válido", longDescription);
        GlobalBrandException exception = assertThrows(GlobalBrandException.class, () -> {
            brandUseCase.saveBrand(brand);
        });
        assertEquals("Descripcion es muy larga", exception.getMessage());
        verify(brandPersistencePort, never()).saveBrand(brand);
    }
    @Test
    void ExceptionIfBrandAlreadyExists() {
        Brand brand = new Brand(1L, "mazda", "carros japoneses");
        when(brandPersistencePort.existsByName("mazda")).thenReturn(true);
        GlobalBrandException exception = assertThrows(GlobalBrandException.class, () -> {
            brandUseCase.saveBrand(brand);
        });
        assertEquals("Marca ya existe", exception.getMessage());
        verify(brandPersistencePort, never()).saveBrand(brand);
    }
}
