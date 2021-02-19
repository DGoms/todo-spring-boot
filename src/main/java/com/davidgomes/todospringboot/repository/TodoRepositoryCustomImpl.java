package com.davidgomes.todospringboot.repository;

import com.davidgomes.todospringboot.model.TodoItem;
import com.davidgomes.todospringboot.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TodoItem> findByUserWithOptions(User user, String search, TodoItem.TodoItemStatus status) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TodoItem> query = cb.createQuery(TodoItem.class);
        Root<TodoItem> todo = query.from(TodoItem.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(todo.get("user"), user));

        if (search != null && search.length() > 0) {
            predicates.add(cb.like(todo.get("title"), "%" + search + "%"));
        }

        if (status != null) {
            predicates.add(cb.equal(todo.get("status"), status));
        }

        query.select(todo).where(
                cb.and(predicates.toArray(new Predicate[0]))
        );

        return entityManager.createQuery(query).getResultList();
    }
}
