package com.boic.balance.user;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserSpecification {
    static Specification<UserJpa> findByUsername(String username) {
        if (username.isEmpty()) return (root, query, cb) -> cb.and();
        return (root, query, cb) -> cb.like(cb.upper(root.get("username")), "%" + username.toUpperCase() + "%");
    }

    static Specification<UserJpa> findByEmail(String email) {
        if (email.isEmpty()) return (root, query, cb) -> cb.and();
        return (root, query, cb) -> cb.equal(root.join("emailList").get("email"), email);
    }

    static Specification<UserJpa> findByPhone(String phone) {
        if (phone.isEmpty()) return (root, query, cb) -> cb.and();
        return (root, query, cb) -> cb.equal(root.join("phoneList").get("phone"), phone);
    }

    static Specification<UserJpa> findByDateOfBirth(String dateOfBirth) {
        if (dateOfBirth.isEmpty()) return (root, query, cb) -> cb.and();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dateOfBirth"), LocalDate.parse(dateOfBirth, formatter));
    }
}
