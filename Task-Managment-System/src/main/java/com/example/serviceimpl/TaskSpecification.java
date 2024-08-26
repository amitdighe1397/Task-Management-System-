package com.example.serviceimpl;

import com.example.model.Task;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TaskSpecification {

    public static Specification<Task> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<Task> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Task> hasDueDate(LocalDate dueDate) {
        return (root, query, criteriaBuilder) -> {
            if (dueDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("dueDate"), dueDate);
        };
    }

}
