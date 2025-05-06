package com.boic.testTask.configuration;

import com.boic.testTask.users.User;
import com.boic.testTask.users.UserJpaMapper;
import com.boic.testTask.users.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserJpaMapper userJpaMapper;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpaMapper.fromJpaEntity(userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));

        // Преобразуем роли из БД в GrantedAuthority
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())));

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
