package com.emazon.stock.api.application.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data

@AllArgsConstructor
@NoArgsConstructor


public class CategoryResponse implements Serializable {
    private String name;
    private String description;
}
