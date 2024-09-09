package com.emazon.stock.api.domain.utils;

public enum ProductConstants {
    EMPTY_NAME_MESSAGE("El nombre no puede estar vacio"),
    ENTITY_ALREADY_EXISTS("ya existe"),
    CATEGORY_NOT_FOUND("Esta categoria no existe"),
    BRAND_NOT_FOUND("Esta marca no existe");

    private final String message;

    public String getMessage() {
        return message;
    }

    ProductConstants(String message) {
        this.message = message;
    }
}
