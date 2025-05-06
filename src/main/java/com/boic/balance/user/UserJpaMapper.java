package com.boic.balance.user;

import com.boic.balance.common.JpaMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserJpaMapper extends JpaMapper<User, UserJpa> {
    @Mapping(target = "username", source = "username")

    User fromJpaEntity(UserJpa jpaEntity);

    @Mapping(target = "username", source = "username")
    UserJpa toJpaEntity(User entity);
}
