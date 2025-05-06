package com.boic.balance.email;

import com.boic.balance.common.JpaMapper;
import com.boic.balance.common.UserOnlyId;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserOnlyId.class})
public interface EmailJpaMapper extends JpaMapper<Email, EmailJpa> {
    @Mapping(target = "user", qualifiedByName = "UserOnlyId")
    Email fromJpaEntity(EmailJpa jpaEntity);

    @Mapping(target = "user", qualifiedByName = "UserJpaOnlyId")
    EmailJpa toJpaEntity(Email entity);
}
