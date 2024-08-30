package com.emazon.stock.api.domain;
import com.emazon.stock.api.domain.exception.EntityAlreadyExistsException;
import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.domain.spi.IBrandPersistencePort;
import com.emazon.stock.api.domain.usecase.BrandUseCase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {

    private final IBrandPersistencePort brandPersistencePortMock = mock(IBrandPersistencePort.class, "brandPersistencePortMock");

    @Test
    void saveBrandAlreadyExistsException() {

        when(brandPersistencePortMock.existsByName("mazda")).thenReturn(true);
        BrandUseCase target = new BrandUseCase(brandPersistencePortMock);
        Brand brand = new Brand();
        brand.setName("mazda");
        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            target.saveBrand(brand);
        });
        assertNotNull(exception);
        assertEquals("Marca  Ya existe", exception.getMessage());
        verify(brandPersistencePortMock).existsByName("mazda");
        verify(brandPersistencePortMock, never()).saveBrand(brand);
    }

    @Test
    void saveBrandSuccessfully() {
        when(brandPersistencePortMock.existsByName("mazda")).thenReturn(false);
        BrandUseCase target = new BrandUseCase(brandPersistencePortMock);
        Brand brand = new Brand();
        brand.setName("mazda");
        target.saveBrand(brand);
        verify(brandPersistencePortMock).existsByName("mazda");
        verify(brandPersistencePortMock).saveBrand(brand);
    }





}
