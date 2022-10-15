package com.textaddict.userapi.repository;

import com.textaddict.userapi.model.Role;
import com.textaddict.userapi.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class UserSpecifications {
    public static Specification<User> hasBookWithTitle(String role) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Role> userRole = root.join("roles");
            return criteriaBuilder.equal(userRole.get("role"), role);
        };
    }
}
