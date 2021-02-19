package com.davidgomes.todospringboot.model;

import com.davidgomes.todospringboot.utils.AbstractEnumConverter;
import com.davidgomes.todospringboot.utils.PersistableEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class TodoItem {

    @Convert(converter = TodoItemStatus.Converter.class)
    @NotNull
    @Column(nullable = false)
    private TodoItemStatus status = TodoItemStatus.TODO;

    @Setter(AccessLevel.NONE)
    @GeneratedValue
    @Id
    private Integer id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    public enum TodoItemStatus implements PersistableEnum<Integer> {
        TODO(1),
        IN_PROGRESS(2),
        DONE(3);

        private final Integer value;

        TodoItemStatus(int value) {
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        public static class Converter extends AbstractEnumConverter<TodoItemStatus, Integer> {
            public Converter() {
                super(TodoItemStatus.class);
            }
        }
    }

    @JsonIgnore
    @NotNull
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    private User user;
}
