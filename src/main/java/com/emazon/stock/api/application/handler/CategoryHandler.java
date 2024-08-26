package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.CategoryRequest;


import com.emazon.stock.api.application.dto.CategoryResponse;
import com.emazon.stock.api.application.mapper.CategoryRequestMapper;

import com.emazon.stock.api.application.mapper.CategoryResponseMapper;
import com.emazon.stock.api.domain.api.ICategoryServicePort;
import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.utils.PagedResult;
import com.emazon.stock.api.domain.utils.Pagination;
import com.emazon.stock.api.domain.utils.SortCriteria;
import com.emazon.stock.api.domain.utils.SortDirection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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
        Pagination pagination = new Pagination(page, size);
        SortDirection sortDirection = SortDirection.valueOf(direction.toUpperCase());
        SortCriteria sortCriteria = new SortCriteria(sortBy, sortDirection);
        PagedResult<Category> categoryPagedResult = categoryServicePort.getAllCategories(pagination, sortCriteria);
        List<CategoryResponse> categoryResponses = categoryPagedResult.getContent()
                .stream()
                .map(categoryResponseMapper::toResponse).toList();
        return new PagedResult<>(categoryResponses, categoryPagedResult.getTotalElements(), categoryPagedResult.getTotalPages());
    }


}



