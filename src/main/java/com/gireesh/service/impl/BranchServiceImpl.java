package com.gireesh.service.impl;

import com.gireesh.mapper.BranchMapper;
import com.gireesh.modal.Branch;
import com.gireesh.modal.Store;
import com.gireesh.modal.User;
import com.gireesh.payload.dto.BranchDTO;
import com.gireesh.repository.BranchRepository;
import com.gireesh.repository.StoreRepository;
import com.gireesh.service.BranchService;
import com.gireesh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;


    @Override
    public BranchDTO createBranch(BranchDTO branchDTO) {
        User currentUser = userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(currentUser.getId()).orElseThrow(() -> new RuntimeException("No store associated with user, " + currentUser.getId()));

        Branch branch = BranchMapper.toEntity(branchDTO, store);
        return BranchMapper.toDTO(branchRepository.save(branch));
    }

    @Override
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO) {
        Branch existingBranch = branchRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find branch with id " + id));

        existingBranch.setBranchName(branchDTO.getBranchName());
        existingBranch.setAddress(branchDTO.getAddress());
        existingBranch.setWorkingDays(branchDTO.getWorkingDays());
        existingBranch.setEmail(branchDTO.getEmail());
        existingBranch.setPhone(branchDTO.getPhone());
        existingBranch.setOpeningTime(branchDTO.getOpeningTime());
        existingBranch.setClosingTime(branchDTO.getClosingTime());
        existingBranch.setUpdatedAt(LocalDateTime.now());

        return BranchMapper.toDTO(branchRepository.save(existingBranch));
    }

    @Override
    public void deleteBranch(Long id) {
        Branch existingBranch = branchRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find branch with id " + id));
        branchRepository.delete(existingBranch);
    }

    @Override
    public List<BranchDTO> getAllBranchesByStoreId(Long storeId) {
        return branchRepository.findByStoreId(storeId).stream().map(BranchMapper::toDTO).toList();
    }

    @Override
    public BranchDTO getBranchById(Long id) {
        return BranchMapper.toDTO(branchRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find branch, " + id)));
    }
}
