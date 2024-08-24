package com.emazon.stock.api.domain.usecase;

import com.emazon.stock.api.domain.api.ICategoryServicePort;
import com.emazon.stock.api.domain.exception.GlobalCategoryException;
import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;
import com.emazon.stock.api.domain.utils.PagedResult;
import com.emazon.stock.api.domain.utils.Pagination;
import com.emazon.stock.api.domain.utils.SortCriteria;


public class CategoryUseCase  implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        validateCategory(category);
        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public PagedResult<Category> getAllCategories(Pagination pagination, SortCriteria sortCriteria) {
        PagedResult<Category> categoryPage = categoryPersistencePort.getAllCategories(pagination, sortCriteria);

        if (categoryPage.getTotalElements() == 0) {
            throw new GlobalCategoryException("No hay categorias");
        }

        return categoryPage;
    }





    private void validateCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new GlobalCategoryException("Nombre no puede ser vacio");
        }
        if (category.getName().length() > 50) {
            throw new GlobalCategoryException("El nombre es muy largo");
        }
        if (category.getDescription() == null || category.getDescription().trim().isEmpty()) {
            throw new GlobalCategoryException("Descripcion no puede ser vacio");
        }
        if (category.getDescription().length() > 90) {
            throw new GlobalCategoryException("Descripcion es muy larga");
        }

        if (categoryPersistencePort.existsByName(category.getName())) {
            throw new GlobalCategoryException("Categoria ya existe");
        }

    }

}
