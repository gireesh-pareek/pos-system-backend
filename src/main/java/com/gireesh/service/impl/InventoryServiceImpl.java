package com.gireesh.service.impl;

import com.gireesh.mapper.InventoryMapper;
import com.gireesh.modal.Branch;
import com.gireesh.modal.Inventory;
import com.gireesh.modal.Product;
import com.gireesh.payload.dto.InventoryDTO;
import com.gireesh.repository.BranchRepository;
import com.gireesh.repository.InventoryRepository;
import com.gireesh.repository.ProductRepository;
import com.gireesh.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        Branch existingBranch = branchRepository.findById(inventoryDTO.getBranchId()).orElseThrow(
                () -> new RuntimeException("Could not find branch with id " + inventoryDTO.getBranchId())
        );

        Product existingProduct = productRepository.findById(inventoryDTO.getProductId()).orElseThrow(
                () -> new RuntimeException("Could not find product with id " + inventoryDTO.getProductId())
        );

        return InventoryMapper.toDTO(inventoryRepository.save(InventoryMapper.toEntity(inventoryDTO, existingBranch, existingProduct)));
    }

    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) {
        Inventory existingInventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find inventory with id " + id));

        existingInventory.setQuantity(inventoryDTO.getQuantity());

        return InventoryMapper.toDTO(inventoryRepository.save(existingInventory));
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory existingInventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find inventory with id " + id));
        inventoryRepository.deleteById(id);
    }

    @Override
    public InventoryDTO getInventoryById(Long id) {
        return InventoryMapper.toDTO(inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find inventory with id " + id)));
    }

    @Override
    public InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId) {
       return InventoryMapper.toDTO(inventoryRepository.findByProductIdAndBranchId(productId, branchId));
    }

    @Override
    public List<InventoryDTO> getAllInventoryByBranchId(Long branchId) {
        return inventoryRepository.findByBranchId(branchId).stream().map(InventoryMapper::toDTO).toList();
    }
}
