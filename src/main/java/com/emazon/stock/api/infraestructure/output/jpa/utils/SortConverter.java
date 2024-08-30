package com.emazon.stock.api.infraestructure.output.jpa.utils;

import com.emazon.stock.api.domain.utils.pagination.SortDirection;
import org.springframework.data.domain.Sort;

public class SortConverter {
    public static Sort.Direction convert(SortDirection direction) {
        return Sort.Direction.fromString(direction.name());
    }

}
