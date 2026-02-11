package com.gireesh.service;

import com.gireesh.exception.UserException;
import com.gireesh.payload.dto.UserDTO;
import com.gireesh.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDTO userDto) throws UserException;
    AuthResponse login(UserDTO userDto) throws UserException;
}
