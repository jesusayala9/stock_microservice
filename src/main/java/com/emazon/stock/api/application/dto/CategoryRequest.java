package com.emazon.stock.api.application.dto;

import com.emazon.stock.api.application.utils.CategoryConstraints;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest implements Serializable {

    @NotBlank(message = CategoryConstraints.EMPTY_NAME_MESSAGE)
    @Size(max = CategoryConstraints.NAME_MAX_LENGTH, message = CategoryConstraints.LONG_NAME_MESSAGE)
    private String name;

    @NotBlank(message = CategoryConstraints.EMPTY_DESCRIPTION_MESSAGE)
    @Size(max = CategoryConstraints.DESCRIPTION_MAX_LENGTH, message = CategoryConstraints.LONG_DESCRIPTION_MESSAGE)
    private String description;
}
