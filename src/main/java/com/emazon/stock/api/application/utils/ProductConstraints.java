package com.emazon.stock.api.application.utils;

public class ProductConstraints {

    public static final int NAME_MAX_LENGTH = 50;
    public static final int DESCRIPTION_MAX_LENGTH = 120;
    public static final String EMPTY_NAME_MESSAGE = "Nombre no puede ser vacio";
    public static final String EMPTY_DESCRIPTION_MESSAGE = "Descripcion no puede ser vacio";
    public static final String LONG_NAME_MESSAGE = "Nombre muy largo";
    public static final String LONG_DESCRIPTION_MESSAGE = "Descripcion muy larga";

    public static final String PRODUCT_QUANTITY_REQUIRED = "Cantidad requerida";

    public static final String PRODUCT_PRICE_REQUIRED = "Precio requerido";
    public static final int PRODUCT_MIN_QUANTITY = 0;
    public static final int PRODUCT_PRICE_MIN_VALUE = 0;

    public static final String PRODUCT_BRAND_REQUIRED = "Marca requerida";

    public static final String ID_CATEGORY_REQUIRED = "Categoria requerida";

    public static final int PRODUCT_CATEGORY_MIN_SIZE = 1;
    public static final int PRODUCT_CATEGORY_MAX_SIZE = 3;

    public static final String ID_CATEGORY_SIZE = "Producto debe tener al menos una categoria y maximo 3";


    private ProductConstraints() {
    }
}
