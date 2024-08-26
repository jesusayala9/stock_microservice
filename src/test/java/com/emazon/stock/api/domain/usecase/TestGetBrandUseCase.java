package com.emazon.stock.api.domain.usecase;


import com.emazon.stock.api.domain.exception.GlobalBrandException;

import com.emazon.stock.api.domain.model.Brand;

import com.emazon.stock.api.domain.spi.IBrandPersistencePort;

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
import static org.mockito.Mockito.*;

class TestGetBrandUseCase {



    @InjectMocks
    private BrandUseCase brandUseCase;

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBrandsTest() {
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(1L, "Mazda", "Carros japoneses"));
        PagedResult<Brand> pagedResult = new PagedResult<>(brands, brands.size(), 1);

        when(brandPersistencePort.getAllBrands(pagination, sortCriteria)).thenReturn(pagedResult);
        PagedResult<Brand> result = brandUseCase.getAllBrands(pagination, sortCriteria);
        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals("Mazda", result.getContent().get(0).getName());
        verify(brandPersistencePort, times(1)).getAllBrands(pagination, sortCriteria);
    }

    @Test
    void getAllBrandsEmptyTest() {

        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);
        PagedResult<Brand> pagedResult = new PagedResult<>(new ArrayList<>(), 0, 0);

        when(brandPersistencePort.getAllBrands(pagination, sortCriteria)).thenReturn(pagedResult);

        GlobalBrandException exception = assertThrows(GlobalBrandException.class, () -> {
            brandUseCase.getAllBrands(pagination, sortCriteria);
        });

        assertEquals("No hay marcas.", exception.getMessage());
        verify(brandPersistencePort, times(1)).getAllBrands(pagination, sortCriteria);
    }

    @Test
    void getAllBrandsNullPaginationTest() {

        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);


        GlobalBrandException exception = assertThrows(GlobalBrandException.class, () -> {
            brandUseCase.getAllBrands(null, sortCriteria);
        });

        assertEquals("El parámetro 'pagination' es obligatorio.", exception.getMessage());
        verify(brandPersistencePort, never()).getAllBrands(any(Pagination.class), any(SortCriteria.class));
    }

    @Test
    void getAllBrandsNullSortCriteriaTest() {

        Pagination pagination = new Pagination(0, 10);


        GlobalBrandException exception = assertThrows(GlobalBrandException.class, () -> {
            brandUseCase.getAllBrands(pagination, null);
        });

        assertEquals("El parámetro 'sortCriteria' es obligatorio.", exception.getMessage());
        verify(brandPersistencePort, never()).getAllBrands(any(Pagination.class), any(SortCriteria.class));
    }

    @Test
    void getAllBrandsEmptySortByTest() {

        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("", SortDirection.ASC);


        GlobalBrandException exception = assertThrows(GlobalBrandException.class, () -> {
            brandUseCase.getAllBrands(pagination, sortCriteria);
        });

        assertEquals("El campo 'sortBy' no puede estar vacío.", exception.getMessage());
        verify(brandPersistencePort, never()).getAllBrands(any(Pagination.class), any(SortCriteria.class));
    }

    @Test
    void getAllBrandsNullSortDirectionTest() {

        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", null);


        GlobalBrandException exception = assertThrows(GlobalBrandException.class, () -> {
            brandUseCase.getAllBrands(pagination, sortCriteria);
        });

        assertEquals("El campo 'direction' es obligatorio.", exception.getMessage());
        verify(brandPersistencePort, never()).getAllBrands(any(Pagination.class), any(SortCriteria.class));
    }




}
