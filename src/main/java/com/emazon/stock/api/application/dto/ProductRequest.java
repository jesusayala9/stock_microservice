package com.emazon.stock.api.application.dto;
import com.emazon.stock.api.application.utils.ProductConstraints;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest implements Serializable {

    @NotBlank(message = ProductConstraints.EMPTY_NAME_MESSAGE)
    @Size(max = ProductConstraints.NAME_MAX_LENGTH, message = ProductConstraints.LONG_NAME_MESSAGE)
    private String name;

    @NotBlank(message = ProductConstraints.EMPTY_DESCRIPTION_MESSAGE)
    @Size(max = ProductConstraints.DESCRIPTION_MAX_LENGTH, message = ProductConstraints.LONG_DESCRIPTION_MESSAGE)
    private String description;

    @NotNull(message = ProductConstraints.PRODUCT_QUANTITY_REQUIRED)
    @Min(value = ProductConstraints.PRODUCT_MIN_QUANTITY, message = ProductConstraints.PRODUCT_QUANTITY_REQUIRED)
    private Integer quantity;

    @NotNull(message = ProductConstraints.PRODUCT_PRICE_REQUIRED)
    @Min(value =  ProductConstraints.PRODUCT_PRICE_MIN_VALUE, message = ProductConstraints.PRODUCT_PRICE_REQUIRED)
    private Double price;

    @NotNull(message = ProductConstraints.PRODUCT_BRAND_REQUIRED)
    @Positive(message = ProductConstraints.PRODUCT_BRAND_REQUIRED)
    private Long brandId;

    @NotEmpty(message = ProductConstraints.ID_CATEGORY_REQUIRED)
    @Size(min = ProductConstraints.PRODUCT_CATEGORY_MIN_SIZE, max = ProductConstraints.PRODUCT_CATEGORY_MAX_SIZE, message = ProductConstraints.ID_CATEGORY_SIZE)
    private List<Long> categoryIds;

}
