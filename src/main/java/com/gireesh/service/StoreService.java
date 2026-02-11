package com.gireesh.service;

import com.gireesh.domain.StoreStatus;
import com.gireesh.modal.Store;
import com.gireesh.modal.User;
import com.gireesh.payload.dto.StoreDTO;

import java.util.List;

public interface StoreService {
    StoreDTO createStore(StoreDTO storeDTO, User user);
    StoreDTO getStoreById(Long Id);
    List<StoreDTO> getAllStores();
    Store getStoreByAdmin();
    StoreDTO updateStore(Long id, StoreDTO storeDTO);
    void deleteStore(Long id);
    StoreDTO getStoreByEmployee();
    StoreDTO moderateStore(Long id, StoreStatus storeStatus);
}
