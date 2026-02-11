package com.gireesh.payload.response;

import com.gireesh.payload.dto.UserDTO;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserDTO user;
}
