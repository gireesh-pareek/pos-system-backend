package com.gireesh.mapper;

import com.gireesh.modal.User;
import com.gireesh.payload.dto.UserDTO;

public class UserMapper {


    public static UserDTO toDTO(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setFullName(user.getFullName());
        userDto.setPhone(user.getPhone());
        userDto.setBranchId(user.getBranch() != null ? user.getBranch().getId() : null);
        userDto.setStoreId(user.getStore() != null ? user.getStore().getId() : null);
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        userDto.setLastLoginAt(user.getLastLoginAt());

        return userDto;
    }

    public static User toEntity(UserDTO userDTO){
        return User.builder()
                .email(userDTO.getEmail())
                .fullName(userDTO.getFullName())
                .role(userDTO.getRole())
                .createdAt(userDTO.getCreatedAt())
                .updatedAt(userDTO.getUpdatedAt())
                .lastLoginAt(userDTO.getLastLoginAt())
                .phone(userDTO.getPhone())
                .build();
    }
}
