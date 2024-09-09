package com.emazon.stock.api.domain.utils;

public enum CategoryConstants {

    ENTITY_ALREADY_EXISTS("ya existe"),
    NO_PAGES("No hay"),
    EMPTY_NAME_MESSAGE ("Nombre no puede ser vacio");

    private final String message;

    CategoryConstants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
