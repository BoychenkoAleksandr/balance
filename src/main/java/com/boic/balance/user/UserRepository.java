package com.boic.testTask.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserJpa, Long> {
    Optional<UserJpa> findByUsernameIgnoreCase(String username);
}
