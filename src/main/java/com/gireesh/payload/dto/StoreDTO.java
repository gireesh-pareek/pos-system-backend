package com.gireesh.payload.dto;

import com.gireesh.domain.StoreStatus;
import com.gireesh.modal.StoreContact;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDTO {
    private Long id;

    private String brand;

    private UserDTO storeAdmin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String description;

    private String storeType;

    private StoreStatus status;

    private StoreContact contact;
}
