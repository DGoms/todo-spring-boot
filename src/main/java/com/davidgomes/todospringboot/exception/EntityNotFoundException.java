package com.davidgomes.todospringboot.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BaseHttpException {

    public EntityNotFoundException(String code, String message) {
        super(HttpStatus.NOT_FOUND, code, message);
    }

    public EntityNotFoundException(String message) {
        this("entity_not_found", message);
    }

    @SuppressWarnings("rawtypes")
    public EntityNotFoundException(Class entityClass, Integer id) {
        this("entity_not_found", entityClass.getSimpleName() + " not found with id: " + id);
    }
}
