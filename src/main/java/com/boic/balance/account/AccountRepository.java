package com.boic.balance.account;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountJpa, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT DISTINCT a FROM AccountJpa a WHERE a.user.id = :userId")
    Optional<AccountJpa> findByUserIdWithLock(@Param("userId") Long userId);
}
