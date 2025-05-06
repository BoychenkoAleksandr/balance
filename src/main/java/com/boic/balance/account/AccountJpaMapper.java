package com.boic.balance.account;

import com.boic.balance.common.JpaMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountJpaMapper extends JpaMapper<Account, AccountJpa> {
    @Mapping(target = "balance", source = "balance")
    Account fromJpaEntity(AccountJpa jpaEntity);

    @Mapping(target = "balance", source = "balance")
    AccountJpa toJpaEntity(Account entity);

}
