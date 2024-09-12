package com.emazon.stock.api.infraestructure.output.jpa.repository;
import com.emazon.stock.api.infraestructure.output.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface IProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    boolean existsByName(String name);




}
