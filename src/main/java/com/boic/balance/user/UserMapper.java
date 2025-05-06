package com.boic.balance.user;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    @Mapping(target = "username", source = "username")
    UserDtoOut toOut(User entity);

    default Page<UserDtoOut> toOut(Page<User> page) {
        return page.map(this::toOut);
    }
}
