package com.boic.balance.account;

import com.boic.balance.user.UserJpa;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
@Data
public class AccountJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpa user;

    @Column(name = "start_balance")
    private BigDecimal startBalance;

    @Column(name = "balance")
    private BigDecimal balance;
}
