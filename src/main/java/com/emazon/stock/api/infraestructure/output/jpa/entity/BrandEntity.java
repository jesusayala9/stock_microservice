package com.emazon.stock.api.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brand")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 120 )
    private String description;

    public BrandEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
