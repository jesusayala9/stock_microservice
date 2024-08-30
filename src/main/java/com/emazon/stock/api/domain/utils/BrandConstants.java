package com.emazon.stock.api.domain.utils;

public enum BrandConstants {

    ENTITY_ALREADY_EXISTS("Ya existe");


    private final String message;

    BrandConstants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
