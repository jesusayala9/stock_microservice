package com.emazon.stock.api.application.dto;

import lombok.Data;


@Data
public class CategoryRequest {
    private String name;
    private String description;
}
