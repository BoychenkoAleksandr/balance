package com.boic.balance.phone;

import com.boic.balance.user.UserJpa;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "phone")
@EntityListeners(AuditingEntityListener.class)
@Data
public class PhoneJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpa user;

    @Column(name = "phone")
    private String phone;
}
