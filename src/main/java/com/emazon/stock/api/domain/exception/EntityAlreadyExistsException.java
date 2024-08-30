package com.emazon.stock.api.domain.exception;

import com.emazon.stock.api.domain.utils.CategoryConstants;


public class EntityAlreadyExistsException extends RuntimeException{
        public EntityAlreadyExistsException(String message) {
            super( message  + "  " + CategoryConstants.ENTITY_ALREADY_EXISTS.getMessage()) ;
        }
}
