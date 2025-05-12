package com.boic.balance.phone;

import com.boic.balance.common.CrudService;
import com.boic.balance.common.JpaMapper;
import com.boic.balance.exception.NotFoundException;
import com.boic.balance.exception.OwnerException;
import com.boic.balance.exception.UniqueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneService implements CrudService<Phone, PhoneJpa> {

    private final PhoneJpaMapper phoneJpaMapper;
    private final PhoneRepository phoneRepository;
    private final PhoneCacheService cacheService;

    @Override
    public JpaRepository<PhoneJpa, Long> getRepository() {
        return phoneRepository;
    }

    @Override
    public JpaSpecificationExecutor<PhoneJpa> getExecutor() {
        return phoneRepository;
    }

    @Override
    public JpaMapper<Phone, PhoneJpa> getMapper() {
        return phoneJpaMapper;
    }

    @Transactional
    public Phone findByPhone(String phone) {
        return phoneJpaMapper.fromJpaEntity(phoneRepository.findByPhone(phone)
                .orElse(null));
    }

    @Transactional
    public Phone getById(Long id) {
        return cacheService.getById(id);
    }
    
    @Transactional
    public Phone persistUniquePhone(Phone phoneEntity) {
        if (phoneRepository.findByPhone(phoneEntity.getPhone()).isPresent())
            throw new UniqueException("Phone already exists");
        return persist(phoneEntity);
    }

    @Transactional
    @CachePut(cacheNames = "phone", key = "#id")
    public Phone updatePhone(Long id, Phone request, Long userId) {
        Phone phoneEntity = checkPhone(id, userId);
        if (phoneRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new UniqueException("Phone already exists");
        }
        phoneEntity.setPhone(request.getPhone());
        return persist(phoneEntity);
    }

    @Transactional
    @CacheEvict(cacheNames = "phone", key = "#id")
    public void delete(Long id, Long userId) {
        checkPhone(id, userId);
        phoneRepository.deleteById(id);
    }

    @Transactional
    public Phone checkPhone(Long id, Long userId) {
        final Phone phoneEntity;
        try {
            phoneEntity = cacheService.getById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException("Not found phone with id: " + id);
        }

        if (!phoneEntity.getUser().getId().equals(userId)) {
            log.error("Insufficient rights for this action");
            throw new OwnerException("You are not the owner of this phone");
        }
        return phoneEntity;
    }
}
