package com.davidgomes.todospringboot.utils;

import com.fasterxml.jackson.annotation.JsonValue;

public interface PersistableEnum<T> {

    @JsonValue
    T getValue();
}
