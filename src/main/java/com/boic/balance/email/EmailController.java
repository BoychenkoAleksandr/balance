package com.boic.balance.email;

import com.boic.balance.coniguration.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    private final EmailService emailService;
    private final EmailMapper emailMapper;

    @GetMapping("/{id}")
    public ResponseEntity<EmailDto> getEmailById(@PathVariable Long id) {
        log.debug("Fetching email by id: {}", id);
        Email findEmail = emailService.getById(id);
        log.debug("Successfully fetched email with id: {}", id);
        return ResponseEntity.ok(emailMapper.toOut(findEmail));
    }

    @PostMapping
    public ResponseEntity<EmailDto> createEmail(
            @RequestBody EmailDto request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.debug("Creating new email by user: {}", customUserDetails.getUsername());
        Email savedEmail = emailService.persistUniqueMail(emailMapper.fromIn(request));
        log.info("Email created successfully with id: {}", savedEmail.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(emailMapper.toOut(savedEmail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailDto> updateEmail(
            @PathVariable Long id,
            @RequestBody EmailDto request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.debug("Updating email with id: {} by user: {}", id, customUserDetails.getUsername());
        Email savedEmail = emailService.updateEmail(id, emailMapper.fromIn(request),
                customUserDetails.getId());
        log.info("Email with id {} updated successfully", id);
        return ResponseEntity.ok(emailMapper.toOut(savedEmail));
    }

    @DeleteMapping("/{id}")
    public void deleteEmail(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.debug("Deleting email with id: {} by user: {}", id, customUserDetails.getUsername());
        emailService.delete(id, customUserDetails.getId());
        log.info("Email with id {} deleted successfully", id);
    }
}
