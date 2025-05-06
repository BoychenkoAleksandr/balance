package com.boic.balance.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailJpa, Long>, JpaSpecificationExecutor<EmailJpa> {
    Optional<EmailJpa> findByEmailIgnoreCase(String email);
}
