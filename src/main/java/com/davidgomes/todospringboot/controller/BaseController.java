package com.davidgomes.todospringboot.controller;

import org.apache.commons.collections.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class BaseController {

    private Validator validator;

    @PostConstruct
    public void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    protected <T> void validate(T t) {
        Set<ConstraintViolation<T>> errors = validator.validate(t);
        if (CollectionUtils.isNotEmpty(errors)) {
            throw new ConstraintViolationException(errors);
        }
    }
}
