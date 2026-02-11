package com.gireesh.controller;

import com.gireesh.exception.UserException;
import com.gireesh.mapper.UserMapper;
import com.gireesh.modal.User;
import com.gireesh.payload.dto.UserDTO;
import com.gireesh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwtToken) throws UserException {
        User user = userService.getUserFromJwtToken(jwtToken);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@RequestHeader("Authorization") String jwtToken, @PathVariable Long id) throws UserException {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
