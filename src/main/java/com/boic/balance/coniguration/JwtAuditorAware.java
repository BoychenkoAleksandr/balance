package com.boic.balance.coniguration;

import com.boic.balance.user.UserJpa;
import com.boic.balance.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuditorAware implements AuditorAware<UserJpa> {
    private final UserRepository userRepository;

    @Override
    public Optional<UserJpa> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // Если principal - это CustomUserDetails
            if (principal instanceof CustomUserDetails) {
                final Optional<UserJpa> userJpa = userRepository.findById(((CustomUserDetails) principal).getId());
                if (userJpa.isPresent())
                    return userJpa;
            }
        }
        return Optional.empty();
    }
}
