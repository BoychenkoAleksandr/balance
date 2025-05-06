package com.boic.balance.email;

import com.boic.balance.user.UserJpa;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "email")
@EntityListeners(AuditingEntityListener.class)
@Data
public class EmailJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpa user;

    @Column(name = "email")
    private String email;
}
