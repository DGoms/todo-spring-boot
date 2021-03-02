package com.davidgomes.todospringboot.exception;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(String code, String message) {
        super(code, message);
    }

    public EntityNotFoundException(String message) {
        super("entity_not_found", message);
    }

    @SuppressWarnings("rawtypes")
    public EntityNotFoundException(Class entityClass, Integer id) {
        super("entity_not_found", entityClass.getSimpleName() + " not found with id: " + id);
    }
}
