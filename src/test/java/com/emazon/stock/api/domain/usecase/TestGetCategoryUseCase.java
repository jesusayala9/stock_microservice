package com.emazon.stock.api.domain.usecase;

import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void PagedCategories() {

        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "name"));
        List<Category> categories = Arrays.asList(
                new Category(1L, "Automotriz", "Artículos para autos"),
                new Category(2L, "Electrónica", "Productos electrónicos")
        );
        Page<Category> pagedCategories = new PageImpl<>(categories, pageable, categories.size());

        when(categoryPersistencePort.getAllCategories(pageable)).thenReturn(pagedCategories);


        Page<Category> result = categoryUseCase.getAllCategories(pageable);


        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("Automotriz", result.getContent().get(0).getName());
        verify(categoryPersistencePort, times(1)).getAllCategories(pageable);
    }

    @Test
    void SortedCategories() {

        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "name"));
        List<Category> categories = Arrays.asList(
                new Category(1L, "Electrónica", "Productos electrónicos"),
                new Category(2L, "Automotriz", "Artículos para autos")
        );
        Page<Category> pagedCategories = new PageImpl<>(categories, pageable, categories.size());

        when(categoryPersistencePort.getAllCategories(pageable)).thenReturn(pagedCategories);


        Page<Category> result = categoryUseCase.getAllCategories(pageable);


        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("Electrónica", result.getContent().get(0).getName());
        verify(categoryPersistencePort, times(1)).getAllCategories(pageable);
    }

    @Test
    void EmptyResult() {

        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "name"));
        Page<Category> pagedCategories = Page.empty(pageable);

        when(categoryPersistencePort.getAllCategories(pageable)).thenReturn(pagedCategories);


        Page<Category> result = categoryUseCase.getAllCategories(pageable);


        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        verify(categoryPersistencePort, times(1)).getAllCategories(pageable);
    }
}
