package com.gireesh.service.impl;

import com.gireesh.configuration.JwtProvider;
import com.gireesh.exception.UserException;
import com.gireesh.modal.User;
import com.gireesh.repository.UserRepository;
import com.gireesh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User getUserFromJwtToken(String token) throws UserException {
        String email = jwtProvider.getEmailFromToken(token);
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException("Invalid token"));
    }

    @Override
    public User getCurrentUser() throws UserException {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) throws UserException {
         return userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public User getUserById(Long id) throws UserException {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public List<User> getALlUsers() {
        return userRepository.findAll();
    }
}
