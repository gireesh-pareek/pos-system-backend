package com.gireesh.payload.dto;

import com.gireesh.modal.Branch;
import com.gireesh.modal.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    Long id;

    private Branch branch;

    private Long branchId;

    private Long productId;

    private Product product;

    private int quantity;

    private LocalDateTime lastUpdated;
}
