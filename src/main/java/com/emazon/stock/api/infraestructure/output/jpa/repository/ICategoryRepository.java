package com.emazon.stock.api.infraestructure.output.jpa.repository;

import com.emazon.stock.api.infraestructure.output.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long>{
    boolean existsByName(String name);


}
