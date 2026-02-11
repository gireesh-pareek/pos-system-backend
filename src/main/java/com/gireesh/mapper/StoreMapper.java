package com.gireesh.mapper;

import com.gireesh.modal.Store;
import com.gireesh.modal.User;
import com.gireesh.payload.dto.StoreDTO;

public class StoreMapper {

    public static StoreDTO toDTO(Store store){
        StoreDTO storeDTO = new StoreDTO();

        storeDTO.setId(store.getId());
        storeDTO.setBrand(store.getBrand());
        storeDTO.setDescription(store.getDescription());
        storeDTO.setStoreAdmin(UserMapper.toDTO(store.getStoreAdmin()));
        storeDTO.setStoreType(store.getStoreType());
        storeDTO.setContact(store.getContact());
        storeDTO.setStatus(store.getStatus());
        storeDTO.setCreatedAt(store.getCreatedAt());
        storeDTO.setUpdatedAt(store.getUpdatedAt());

        return storeDTO;
    }

    public static Store toEntity(StoreDTO storeDTO, User storeAdmin){
        Store store = new Store();

        store.setId(storeDTO.getId());
        store.setBrand(storeDTO.getBrand());
        store.setDescription(storeDTO.getDescription());
        store.setStoreAdmin(storeAdmin);
        store.setStoreType(store.getStoreType());
        store.setContact(storeDTO.getContact());
        store.setUpdatedAt(storeDTO.getUpdatedAt());

        return store;
    }
}
