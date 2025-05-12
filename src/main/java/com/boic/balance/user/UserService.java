package com.boic.balance.user;

import com.boic.balance.common.CrudService;
import com.boic.balance.common.JpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements CrudService<User, UserJpa> {

    private final UserJpaMapper userJpaMapper;
    private final UserRepository userRepository;

    public JpaRepository<UserJpa, Long> getRepository() {
        return userRepository;
    }

    @Override
    public JpaSpecificationExecutor<UserJpa> getExecutor() {
        return userRepository;
    }

    public JpaMapper<User, UserJpa> getMapper() {
        return userJpaMapper;
    }

    public Page<User> findAllUser(String username, String email, String phone, String dateOfBirth, Pageable pageable) {
        return findBySpec(UserSpecification.findByUsername(username)
                .and(UserSpecification.findByEmail(email)
                .and(UserSpecification.findByPhone(phone))
                .and(UserSpecification.findByDateOfBirth(dateOfBirth))
                ), pageable);
    }
}
