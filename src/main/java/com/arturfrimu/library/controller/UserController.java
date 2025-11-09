package com.arturfrimu.library.controller;

import com.arturfrimu.library.dto.response.UserResponse;
import com.arturfrimu.library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findUsers(Pageable pageable) {
        log.info("Received request to find all users with pagination");
        var response = userService.findUsers(pageable);
        return ResponseEntity.ok(response);
    }
}