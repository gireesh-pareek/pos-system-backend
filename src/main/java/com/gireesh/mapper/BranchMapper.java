package com.gireesh.mapper;

import com.gireesh.modal.Branch;
import com.gireesh.modal.Store;
import com.gireesh.payload.dto.BranchDTO;

import java.time.LocalDateTime;

public class BranchMapper {

    public static BranchDTO toDTO(Branch branch){
        return BranchDTO.builder()
                .id(branch.getId())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .email(branch.getEmail())
                .closingTime(branch.getClosingTime())
                .openingTime(branch.getOpeningTime())
                .workingDays(branch.getWorkingDays())
                .storeId(branch.getStore() != null ? branch.getStore().getId() : null)
                .store(branch.getStore())
                .manager(branch.getManager())
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .build();
    }

    public static Branch toEntity(BranchDTO branchDTO, Store store){
        return Branch.builder()
                .branchName(branchDTO.getBranchName())
                .address(branchDTO.getAddress())
                .store(store)
                .manager(branchDTO.getManager())
                .email(branchDTO.getEmail())
                .phone(branchDTO.getPhone())
                .closingTime(branchDTO.getClosingTime())
                .openingTime(branchDTO.getOpeningTime())
                .workingDays(branchDTO.getWorkingDays())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
