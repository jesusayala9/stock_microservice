package com.emazon.stock.api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {


    private String name;


    private String description;


    private Integer quantity;


    private Double price;


    private Long brandId;


    private List<Long> categoryIds;
}
