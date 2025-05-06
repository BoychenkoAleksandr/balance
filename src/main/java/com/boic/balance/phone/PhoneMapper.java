package com.boic.balance.phone;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PhoneMapper {
    @Mapping(target = "phone", source = "phone")
    PhoneDto toOut(Phone entity);

    @Mapping(target = "phone", source = "phone")
    Phone fromIn(PhoneDto entity);
}
