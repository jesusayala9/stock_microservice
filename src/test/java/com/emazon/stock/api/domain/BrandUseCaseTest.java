package com.emazon.stock.api.domain;
import com.emazon.stock.api.domain.exception.EntityAlreadyExistsException;
import com.emazon.stock.api.domain.exception.PageException;
import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.domain.spi.IBrandPersistencePort;
import com.emazon.stock.api.domain.usecase.BrandUseCase;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.domain.utils.pagination.SortDirection;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

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

    @Test
    void getAllBrands() {
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        List<Brand> brands = List.of(new Brand(1L, "mazda", "carros"), new Brand(2L, "yamaha", "motos"));
        PagedResult<Brand> pagedResult = new PagedResult<>(brands, brands.size(), pagination.getSize());
        BrandUseCase brandUseCase = new BrandUseCase(brandPersistencePortMock);
        when(brandPersistencePortMock.getAllBrands(pagination, sortCriteria)).thenReturn(pagedResult);
        PagedResult<Brand> result = brandUseCase.getAllBrands(pagination, sortCriteria);
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        verify(brandPersistencePortMock, times(1)).getAllBrands(pagination, sortCriteria);
    }

    @Test
    void getAllBrandsException() {
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        PagedResult<Brand> emptyPagedResult = new PagedResult<>(Collections.emptyList(), 0, pagination.getSize());
        BrandUseCase brandUseCase = new BrandUseCase(brandPersistencePortMock);
        when(brandPersistencePortMock.getAllBrands(pagination, sortCriteria)).thenReturn(emptyPagedResult);
        assertThrows(PageException.class, () -> {
            brandUseCase.getAllBrands(pagination, sortCriteria);
        });
        verify(brandPersistencePortMock, times(1)).getAllBrands(pagination, sortCriteria);
    }





}
