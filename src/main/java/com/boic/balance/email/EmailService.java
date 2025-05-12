package com.boic.balance.email;

import com.boic.balance.common.CrudService;
import com.boic.balance.common.JpaMapper;
import com.boic.balance.exception.OwnerException;
import com.boic.balance.exception.NotFoundException;
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
public class EmailService implements CrudService<Email, EmailJpa> {

    private final EmailJpaMapper emailJpaMapper;
    private final EmailRepository emailRepository;
    private final EmailCacheService cacheService;
    @Override
    public JpaRepository<EmailJpa, Long> getRepository() {
        return emailRepository;
    }

    @Override
    public JpaSpecificationExecutor<EmailJpa> getExecutor() {
        return emailRepository;
    }

    @Override
    public JpaMapper<Email, EmailJpa> getMapper() {
        return emailJpaMapper;
    }

    @Transactional
    public Email findByEmail(String email) {
        return emailJpaMapper.fromJpaEntity(emailRepository.findByEmail(email)
                .orElse(null));
    }

    @Transactional
    public Email getById(Long id) {
        return cacheService.getById(id);
    }

    @Transactional
    public Email persistUniqueMail(Email emailEntity) {
        if (emailRepository.findByEmail(emailEntity.getEmail()).isPresent())
            throw new UniqueException("Email already exists");
        return persist(emailEntity);
    }

    @Transactional
    @CachePut(cacheNames = "email", key = "#id")
    public Email updateEmail(Long id, Email request, Long userId) {
        Email emailEntity = checkEmail(id, userId);
        if (emailRepository.findByEmail(request.getEmail()).isPresent())
            throw new UniqueException("Email already exists");
        emailEntity.setEmail(request.getEmail());
        return this.persist(emailEntity);
    }

    @Transactional
    @CacheEvict(cacheNames = "phone", key = "#id")
    public void delete(Long id, Long userId) {
        checkEmail(id, userId);
        emailRepository.deleteById(id);
    }

    @Transactional
    public Email checkEmail(Long id, Long userId) {
        final Email emailEntity;
        try {
            emailEntity = cacheService.getById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException("Not found email with id: " + id);
        }

        if (!emailEntity.getUser().getId().equals(userId)) {
            log.error("Insufficient rights for this action");
            throw new OwnerException("You are not the owner of this email");
        }
        return emailEntity;
    }
}
