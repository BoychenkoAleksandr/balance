package com.boic.balance.account;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {
    @Mapping(target = "balance", source = "balance")
    AccountDtoOut toOut(Account entity);
}
