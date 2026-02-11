package com.gireesh.mapper;

import com.gireesh.modal.Branch;
import com.gireesh.modal.Inventory;
import com.gireesh.modal.Product;
import com.gireesh.payload.dto.InventoryDTO;

public class InventoryMapper {
    public static InventoryDTO toDTO(Inventory inventory){
        return InventoryDTO.builder()
                .id(inventory.getId())
                .branch(inventory.getBranch())
                .branchId(inventory.getBranch() != null ? inventory.getBranch().getId() : null)
                .productId(inventory.getProduct() != null ? inventory.getProduct().getId() : null)
                .product((inventory.getProduct()))
                .quantity(inventory.getQuantity())
                .build();
    }

    public static Inventory toEntity(InventoryDTO inventoryDTO, Branch branch, Product product){
        return Inventory.builder()
                .branch(branch)
                .product(product)
                .quantity(inventoryDTO.getQuantity())
                .build();
    }
}
