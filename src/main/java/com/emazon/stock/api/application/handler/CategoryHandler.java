package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.CategoryRequest;


import com.emazon.stock.api.application.dto.CategoryResponse;
import com.emazon.stock.api.application.mapper.CategoryRequestMapper;

import com.emazon.stock.api.application.mapper.CategoryResponseMapper;
import com.emazon.stock.api.domain.api.ICategoryServicePort;
import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.domain.utils.pagination.SortDirection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler{
    private final ICategoryServicePort categoryServicePort;
    private final CategoryRequestMapper categoryRequestMapper;

    private final CategoryResponseMapper categoryResponseMapper;



    @Override
    public void saveCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.saveCategory(category);
    }

    @Override
    public PagedResult<CategoryResponse> getAllCategories(int page, int size, String sortBy, String direction) {
        // Crear objetos Pagination y SortCriteria
        Pagination pagination = new Pagination(page, size);

        // Convertir la dirección de ordenación en un enum SortDirection
        SortDirection sortDirection = SortDirection.valueOf(direction.toUpperCase());
        SortCriteria sortCriteria = new SortCriteria(sortBy, sortDirection);

        // Obtener el resultado paginado de categorías
        PagedResult<Category> categoryPagedResult = categoryServicePort.getAllCategories(pagination, sortCriteria);

        // Convertir cada Category a CategoryResponse
        List<CategoryResponse> categoryResponses = categoryPagedResult.getContent()
                .stream()
                .map(categoryResponseMapper::toResponse).toList();

        // Devolver el resultado paginado con las respuestas
        return new PagedResult<>(categoryResponses, categoryPagedResult.getTotalElements(), categoryPagedResult.getTotalPages());
    }


}



