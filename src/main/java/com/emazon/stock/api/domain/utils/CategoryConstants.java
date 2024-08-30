package com.emazon.stock.api.domain.utils;

public enum CategoryConstants {

    ENTITY_ALREADY_EXISTS("Ya existe"),
    NO_PAGES("No hay");

    private final String message;

    CategoryConstants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
