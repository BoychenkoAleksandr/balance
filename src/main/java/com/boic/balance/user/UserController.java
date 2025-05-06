package com.boic.balance.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    @GetMapping("/findAllUsers")
    public ResponseEntity<Page<UserDtoOut>> findAllUsers(
            @RequestBody UserDtoIn dto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userMapper.toOut(userService.findAllUser(
                dto.getUsername(), dto.getEmail(), dto.getPhone(),
                dto.getDateOfBirth(), pageable)));
    }
}
