package com.emazon.stock.api.infraestructure.output.jpa.repository;
import com.emazon.stock.api.infraestructure.output.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(String name);

}
