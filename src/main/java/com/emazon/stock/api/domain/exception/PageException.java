package com.emazon.stock.api.domain.exception;

import com.emazon.stock.api.domain.utils.CategoryConstants;

public class PageException extends RuntimeException{
    public PageException(String message) {
        super(CategoryConstants.NO_PAGES.getMessage() + " " + message );
    }
}
