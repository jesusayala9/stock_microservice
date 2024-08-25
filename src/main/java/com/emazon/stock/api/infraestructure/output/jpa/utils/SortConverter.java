package com.emazon.stock.api.infraestructure.output.jpa.utils;

import com.emazon.stock.api.domain.utils.SortDirection;
import org.springframework.data.domain.Sort;

public class SortConverter {

    private SortConverter() {
        throw new UnsupportedOperationException("utility class");
    }
    public static Sort.Direction convert(SortDirection direction) {
        return Sort.Direction.fromString(direction.name());
    }

}
