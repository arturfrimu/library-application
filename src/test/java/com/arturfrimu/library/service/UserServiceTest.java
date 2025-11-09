package com.arturfrimu.library.service;

import com.arturfrimu.library.dto.response.UserResponse;
import com.arturfrimu.library.entity.Address;
import com.arturfrimu.library.entity.User;
import com.arturfrimu.library.mapper.UserMapper;
import com.arturfrimu.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void shouldFindUsers() {
        var userId1 = UUID.randomUUID();
        var userId2 = UUID.randomUUID();
        var addressId1 = UUID.randomUUID();
        var addressId2 = UUID.randomUUID();
        var pageable = PageRequest.of(0, 10);

        var address1 = Address.builder()
                .address("Address 1")
                .zipCode("12345")
                .city("City 1")
                .build();
        address1.setId(addressId1);

        var address2 = Address.builder()
                .address("Address 2")
                .zipCode("54321")
                .city("City 2")
                .build();
        address2.setId(addressId2);

        var user1 = User.builder()
                .email("user1@example.com")
                .firstName("John")
                .lastName("Doe")
                .address(address1)
                .build();
        user1.setId(userId1);

        var user2 = User.builder()
                .email("user2@example.com")
                .firstName("Jane")
                .lastName("Smith")
                .address(address2)
                .build();
        user2.setId(userId2);

        var now = Instant.now();
        var userResponse1 = new UserResponse(
                userId1,
                "user1@example.com",
                "John",
                "Doe",
                addressId1,
                true,
                now,
                now
        );

        var userResponse2 = new UserResponse(
                userId2,
                "user2@example.com",
                "Jane",
                "Smith",
                addressId2,
                true,
                now,
                now
        );

        var users = List.of(user1, user2);
        var page = new PageImpl<>(users, pageable, 2);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(userMapper.toResponse(user1)).thenReturn(userResponse1);
        when(userMapper.toResponse(user2)).thenReturn(userResponse2);

        var result = userService.findUsers(pageable);

        assertNotNull(result, "Result must not be null");
        assertEquals(2, result.getTotalElements(), "Total elements must be 2");
        assertEquals(2, result.getContent().size(), "Content size must be 2");
        assertEquals(userId1, result.getContent().get(0).id(), "First user ID must match");
        assertEquals(userId2, result.getContent().get(1).id(), "Second user ID must match");
        assertEquals("user1@example.com", result.getContent().get(0).email(), "First user email must match");
        assertEquals("user2@example.com", result.getContent().get(1).email(), "Second user email must match");

        verify(userRepository).findAll(pageable);
        verify(userMapper, times(2)).toResponse(any(User.class));
    }

    @Test
    void shouldReturnEmptyPageWhenNoUsersExist() {
        var pageable = PageRequest.of(0, 10);
        var emptyPage = new PageImpl<User>(List.of(), pageable, 0);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        var result = userService.findUsers(pageable);

        assertNotNull(result, "Result must not be null");
        assertTrue(result.isEmpty(), "Result must be empty");
        assertEquals(0, result.getTotalElements(), "Total elements must be 0");

        verify(userRepository).findAll(pageable);
        verify(userMapper, never()).toResponse(any(User.class));
    }
}

