package com.emazon.stock.api.application.utils;

public class BrandConstraints {

    public static final int NAME_MAX_LENGTH = 50;
    public static final int DESCRIPTION_MAX_LENGTH = 120;
    public static final String EMPTY_NAME_MESSAGE = "Nombre no puede ser vacio";
    public static final String EMPTY_DESCRIPTION_MESSAGE = "Descripcion no puede ser vacio";

    public static final String LONG_NAME_MESSAGE = "Nombre muy largo";
    public static final String LONG_DESCRIPTION_MESSAGE = "Descripcion muy larga";

    private BrandConstraints() {
    }

}
