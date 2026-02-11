package com.gireesh.service.impl;

import com.gireesh.domain.StoreStatus;
import com.gireesh.exception.UserException;
import com.gireesh.mapper.StoreMapper;
import com.gireesh.modal.Store;
import com.gireesh.modal.StoreContact;
import com.gireesh.modal.User;
import com.gireesh.payload.dto.StoreDTO;
import com.gireesh.repository.StoreRepository;
import com.gireesh.service.StoreService;
import com.gireesh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public StoreDTO createStore(StoreDTO storeDTO, User user) {
        Store store = StoreMapper.toEntity(storeDTO, user);
        return StoreMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public StoreDTO getStoreById(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));
        return StoreMapper.toDTO(store);
    }

    @Override
    public List<StoreDTO> getAllStores() {
        return  storeRepository.findAll().stream().map(StoreMapper::toDTO).toList();
    }

    @Override
    public Store getStoreByAdmin() {
        User admin = userService.getCurrentUser();
        return storeRepository.findByStoreAdminId(admin.getId()).orElseThrow(() -> new RuntimeException("Store not found."));
    }

    @Override
    public StoreDTO updateStore(Long id, StoreDTO storeDTO) {
        User currentUser = userService.getCurrentUser();

        Store existing = storeRepository.findByStoreAdminId(currentUser.getId()).orElseThrow(() -> new RuntimeException("Store not found."));

        existing.setBrand(storeDTO.getBrand());
        existing.setDescription(storeDTO.getDescription());

        if(storeDTO.getStoreType() != null){
            existing.setStoreType(storeDTO.getStoreType());
        }

        if(storeDTO.getContact() != null){
            StoreContact contact = StoreContact.builder()
                    .address(storeDTO.getContact().getAddress())
                    .phone(storeDTO.getContact().getPhone())
                    .email(storeDTO.getContact().getEmail())
                    .build();
            existing.setContact(contact);
        }
        return StoreMapper.toDTO(storeRepository.save(existing));
    }

    @Override
    public void deleteStore(Long id) {
        Store store = getStoreByAdmin();
        storeRepository.delete(store);
    }

    @Override
    public StoreDTO getStoreByEmployee() {
        User currentUser = userService.getCurrentUser();
        if(currentUser == null) throw new UserException("User does not have permission to access this store.");
        return StoreMapper.toDTO(currentUser.getStore());
    }

    @Override
    public StoreDTO moderateStore(Long id, StoreStatus storeStatus) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found."));
        store.setStatus(storeStatus);
        return StoreMapper.toDTO(storeRepository.save(store));
    }
}
