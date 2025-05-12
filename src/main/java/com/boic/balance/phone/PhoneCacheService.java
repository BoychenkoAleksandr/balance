package com.boic.balance.phone;

import com.boic.balance.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhoneCacheService {
    private final PhoneJpaMapper phoneJpaMapper;
    private final PhoneRepository phoneRepository;

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "phone", key = "#id")
    public Phone getById(Long id) {
        return phoneJpaMapper.fromJpaEntity(phoneRepository.findById(id))
                .orElseThrow(() -> new NotFoundException("Not found with id: " + id));
    }
}
