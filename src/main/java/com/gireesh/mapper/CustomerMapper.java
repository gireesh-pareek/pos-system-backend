package com.gireesh.mapper;

import com.gireesh.modal.Customer;
import com.gireesh.payload.dto.CustomerDTO;

public class CustomerMapper {

    private CustomerMapper() {
    }

    public static Customer toEntity(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        return Customer.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static CustomerDTO toDTO(Customer entity) {
        if (entity == null) {
            return null;
        }

        return CustomerDTO.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

