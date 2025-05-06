package com.boic.testTask.users;

import com.boic.testTask.common.CrudService;
import com.boic.testTask.common.JpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaMapper userJpaMapper;
    private final UserRepository userRepository;

    public JpaRepository<UserJpa, Long> getRepository() {
        return userRepository;
    }

    public JpaMapper<User, UserJpa> getMapper() {
        return userJpaMapper;
    }

    @Transactional
    public User findByUsernameIgnoreCase(String username) {
        return userJpaMapper.fromJpaEntity(userRepository.findByUsernameIgnoreCase(username)
                .orElse(null));
    }

    @Transactional
    public User persist(User user) {
        return getMapper().fromJpaEntity(
                getRepository().save(
                        getMapper().toJpaEntity(user)));
    }
}
