package com.davidgomes.todospringboot.model;

import com.davidgomes.todospringboot.utils.PersistableEnum;
import com.davidgomes.todospringboot.utils.db_converter.AbstractEnumConverter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Data
public class TodoItem {

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
    @Convert(converter = TodoItemStatus.Converter.class)
    @NotNull
    @Column(nullable = false)
    private TodoItemStatus status = TodoItemStatus.TODO;
    @Getter
    @Column(nullable = false, updatable = false, columnDefinition = "datetime default current_timestamp")
    private Instant createdAt;

    @JsonIgnore
    @ManyToOne(optional = false)
    private User user;

    @Getter
    @Column(nullable = false, columnDefinition = "datetime default current_timestamp")
    private Instant updatedAt;

    /*
     * JPA lifecycle
     */

    @PrePersist
    protected void prePersist() {
        createdAt = updatedAt = Instant.now();
    }

    @PreUpdate
    protected void preUpdate() {
        updatedAt = Instant.now();
    }

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

        @JsonCreator
        public static TodoItemStatus valueToEnum(Integer value) {
            for (TodoItemStatus item : values()) {
                if (item.getValue().equals(value)) return item;
            }

            return null;
        }

    }
}
