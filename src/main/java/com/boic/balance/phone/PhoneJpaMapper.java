package com.boic.balance.phone;

import com.boic.balance.common.JpaMapper;
import com.boic.balance.common.UserOnlyId;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserOnlyId.class})
public interface PhoneJpaMapper extends JpaMapper<Phone, PhoneJpa> {
    @Mapping(target = "user", qualifiedByName = "UserOnlyId")
    Phone fromJpaEntity(PhoneJpa jpaEntity);

    @Mapping(target = "user", qualifiedByName = "UserJpaOnlyId")
    PhoneJpa toJpaEntity(Phone entity);
}
