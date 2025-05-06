package com.boic.balance.email;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EmailMapper {
    @Mapping(target = "email", source = "email")
    EmailDto toOut(Email entity);

    @Mapping(target = "email", source = "email")
    Email fromIn(EmailDto entity);
}
