package com.davidgomes.todospringboot.model;

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

    @NotNull
    @Column(nullable = false)
    private TodoItemStatus status = TodoItemStatus.TODO;

    @JsonIgnore
    @NotNull
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    private User user;

    public enum TodoItemStatus {
        TODO,
        IN_PROGRESS,
        DONE
    }


}
