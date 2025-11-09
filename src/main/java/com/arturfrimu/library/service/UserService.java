package com.arturfrimu.library.service;

import com.arturfrimu.library.dto.response.UserResponse;
import com.arturfrimu.library.mapper.UserMapper;
import com.arturfrimu.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserService {

    UserMapper userMapper;
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<UserResponse> findUsers(Pageable pageable) {
        log.info("Fetching all users with pagination - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());

        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }
}