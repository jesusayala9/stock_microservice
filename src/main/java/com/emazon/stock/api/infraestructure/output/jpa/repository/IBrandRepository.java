package com.emazon.stock.api.infraestructure.output.jpa.repository;
import com.emazon.stock.api.infraestructure.output.jpa.entity.BrandEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {

    boolean existsByName(String name);

    boolean existsById(Long id);



}
