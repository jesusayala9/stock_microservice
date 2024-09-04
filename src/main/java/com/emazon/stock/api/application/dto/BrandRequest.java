package com.emazon.stock.api.application.dto;

import com.emazon.stock.api.application.utils.BrandConstraints;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {
    @NotBlank(message = BrandConstraints.EMPTY_NAME_MESSAGE)
    @Size(max = BrandConstraints.NAME_MAX_LENGTH, message = BrandConstraints.LONG_NAME_MESSAGE)
    private String name;
    @NotBlank(message = BrandConstraints.EMPTY_DESCRIPTION_MESSAGE)
    @Size(max = BrandConstraints.DESCRIPTION_MAX_LENGTH, message = BrandConstraints.LONG_DESCRIPTION_MESSAGE)
    private String description;
}
