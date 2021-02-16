package com.davidgomes.todospringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Collection;
import java.util.Set;

@ToString
@Entity
public class User implements UserDetails {

    @JsonIgnore
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @Setter
    @NotNull
    private String password;

    @JsonIgnore
    @Setter
    @Column(nullable = false)
    private boolean enabled = true;

    @Getter
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Getter
    @Column(nullable = false)
    private Instant updatedAt;

    @Getter
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<TodoItem> todoItems;


    /*
     * Getters / Setters
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void addTodoItem(TodoItem todoItem) {
        todoItems.add(todoItem);
    }

    public void removeTodoItem(TodoItem todoItem) {
        todoItems.remove(todoItem);
    }

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
}