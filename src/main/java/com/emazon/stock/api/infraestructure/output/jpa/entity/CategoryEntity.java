package com.emazon.stock.api.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 90 )
    private String description;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private Set<ProductEntity> products = new HashSet<>();

    public CategoryEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
