package com.boic.balance.common;

import com.boic.balance.user.User;
import com.boic.balance.user.UserJpa;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserOnlyId {
    @Named("UserOnlyId")
    default User onlyId(UserJpa jpaEntity) {
        if (jpaEntity == null)
            return null;
        User user = new User();
        user.setId(jpaEntity.getId());
        return user;
    }

    @Named("UserJpaOnlyId")
    default UserJpa onlyId(User entity) {
        if (entity == null)
            return null;
        UserJpa userJpa = new UserJpa();
        userJpa.setId(entity.getId());
        return userJpa;
    }
}
