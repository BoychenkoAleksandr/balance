package com.boic.balance.phone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneJpa, Long>, JpaSpecificationExecutor<PhoneJpa> {
    Optional<PhoneJpa> findByPhone(String phone);
}
