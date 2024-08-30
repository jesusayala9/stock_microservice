package com.emazon.stock.api.domain.usecase;
import com.emazon.stock.api.domain.api.ICategoryServicePort;

import com.emazon.stock.api.domain.exception.EntityAlreadyExistsException;
import com.emazon.stock.api.domain.exception.PageException;

import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;


public class CategoryUseCase  implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        if (categoryPersistencePort.existsByName(category.getName())) {
            throw new EntityAlreadyExistsException("Categoria");
        }
        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public PagedResult<Category> getAllCategories(Pagination pagination, SortCriteria sortCriteria) {
        PagedResult<Category> categoryPage = categoryPersistencePort.getAllCategories(pagination, sortCriteria);
        if (categoryPage.getTotalElements() == 0) {
            throw new PageException("Categorias");
        }
        return categoryPage;
    }

}
