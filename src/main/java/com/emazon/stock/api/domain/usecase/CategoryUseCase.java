package com.emazon.stock.api.domain.usecase;

import com.emazon.stock.api.domain.api.ICategoryServicePort;
import com.emazon.stock.api.domain.model.Category;
import com.emazon.stock.api.domain.spi.ICategoryPersistencePort;

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



    private void validateCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede ser vacio");
        }
        if (category.getName().length() > 50) {
            throw new IllegalArgumentException("El nombre es muy largo");
        }
        if (category.getDescription() == null || category.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Descripcion no puede ser vacio");
        }
        if (category.getDescription().length() > 90) {
            throw new IllegalArgumentException("Descripcion es muy larga");
        }

        if (categoryPersistencePort.existsByName(category.getName())) {
            throw new IllegalArgumentException("Categoria ya existe");
        }

    }


}
