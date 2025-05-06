package com.boic.balance.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserJpa, Long>, JpaSpecificationExecutor<UserJpa> {
    Optional<UserJpa> findByUsernameIgnoreCase(String username);
}
