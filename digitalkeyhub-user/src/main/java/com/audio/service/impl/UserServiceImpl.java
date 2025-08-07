package com.audio.service.impl;

import com.audio.dto.response.UserServiceResponse;
import com.audio.mapper.UserMapper;
import com.audio.repository.UserRepository;
import com.audio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;

    @Override
    @Cacheable(value = "users", key = "#id")
    @Transactional(readOnly = true)
    public Optional<UserServiceResponse> findById(UUID id) {
        return userRepo.findById(id)
                .map(userMapper::toUserResponseDto);
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    @Transactional(readOnly = true)
    public Optional<UserServiceResponse> findByEmail(String email) {
        return userRepo.findByEmail(email)
                .map(userMapper::toUserResponseDto);
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void deleteUser(UUID userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public boolean existsById(UUID userId) {
        return userRepo.existsById(userId);
    }
}