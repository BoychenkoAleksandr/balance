package com.boic.balance.phone;

import com.boic.balance.coniguration.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/phone")
@RequiredArgsConstructor
@Slf4j
public class PhoneController {
    private final PhoneService phoneService;
    private final PhoneMapper phoneMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PhoneDto> getPhoneById(@PathVariable Long id) {
        log.debug("Fetching phone by id: {}", id);
        Phone findPhone = phoneService.getById(id);
        log.debug("Successfully fetched news with id: {}", id);
        return ResponseEntity.ok(phoneMapper.toOut(findPhone));
    }

    @PostMapping
    public ResponseEntity<PhoneDto> createPhone(
            @RequestBody @Valid PhoneDto request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.debug("Creating new phone by user: {}", customUserDetails.getUsername());
        Phone savedPhone = phoneService.persistUniquePhone(phoneMapper.fromIn(request));
        log.info("Phone created successfully with id: {}", savedPhone.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(phoneMapper.toOut(savedPhone));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhoneDto> updatePhone(
            @PathVariable Long id,
            @RequestBody @Valid PhoneDto request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.debug("Updating phone with id: {} by user: {}", id, customUserDetails.getUsername());
        Phone savedPhone = phoneService.updatePhone(id, phoneMapper.fromIn(request),
                customUserDetails.getId());
        log.info("Phone with id {} updated successfully", id);
        return ResponseEntity.ok(phoneMapper.toOut(savedPhone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.debug("Deleting phone with id: {} by user: {}", id, customUserDetails.getUsername());
        phoneService.delete(id, customUserDetails.getId());
        log.info("Phone with id {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
