package com.boic.balance.email;

import com.boic.balance.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailCacheService {
    private final EmailJpaMapper emailJpaMapper;
    private final EmailRepository emailRepository;

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "email", key = "#id")
    public Email getById(Long id) {
        return emailJpaMapper.fromJpaEntity(emailRepository.findById(id))
                .orElseThrow(() -> new NotFoundException("Not found with id: " + id));
    }
}
