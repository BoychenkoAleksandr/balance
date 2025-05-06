package com.boic.balance.phone;

import com.boic.balance.common.CrudService;
import com.boic.balance.common.JpaMapper;
import com.boic.balance.exception.NotFoundException;
import com.boic.balance.exception.OwnerException;
import com.boic.balance.exception.UniqueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Phone persistUniqueMail(Phone phoneEntity) {
        if (phoneRepository.findByPhone(phoneEntity.getPhone()).isPresent())
            throw new UniqueException("Phone already exists");
        return persist(phoneEntity);
    }

    @Transactional
    public Phone updatePhone(Long id, Phone request, Long userId) {
        final Phone phoneEntity;
        try {
            phoneEntity = this.getById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException("Not found phone with id: " + id);
        }

        if (!phoneEntity.getUser().getId().equals(userId)) {
            log.error("Insufficient rights for this action");
            throw new OwnerException("You are not the owner of this phone");
        }
        phoneEntity.setPhone(request.getPhone());
        return this.persist(phoneEntity);
    }

    public void delete(Long id, Long userId) {
        final Phone phoneEntity;
        try {
            phoneEntity = this.getById(id);
        } catch (NotFoundException e) {
            throw new NotFoundException("Not found phone with id: " + id);
        }

        if (!phoneEntity.getUser().getId().equals(userId)) {
            log.error("Insufficient rights for this action");
            throw new OwnerException("You are not the owner of this phone");
        }

        phoneRepository.delete(phoneJpaMapper.toJpaEntity(phoneEntity));
    }
}
