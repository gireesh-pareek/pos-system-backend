package com.gireesh.controller;

import com.gireesh.domain.StoreStatus;
import com.gireesh.mapper.StoreMapper;
import com.gireesh.modal.User;
import com.gireesh.payload.dto.StoreDTO;
import com.gireesh.payload.response.APIResponse;
import com.gireesh.service.StoreService;
import com.gireesh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO, @RequestHeader("Authorization") String jwtToken){
        User user = userService.getUserFromJwtToken(jwtToken);
        return ResponseEntity.ok(storeService.createStore(storeDTO, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable Long id, @RequestHeader("Authorization") String jwtToken){
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStores(@RequestHeader("Authorization") String jwtToken){
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/admin")
    public ResponseEntity<StoreDTO> getStoreByAdmin(@RequestHeader("Authorization") String jwtToken){
        return ResponseEntity.ok(StoreMapper.toDTO(storeService.getStoreByAdmin()));
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDTO> getStoreByEmployee(@RequestHeader("Authorization") String jwtToken){
        return ResponseEntity.ok(storeService.getStoreByEmployee());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(@PathVariable Long id, @RequestBody StoreDTO storeDTO){
        return ResponseEntity.ok(storeService.updateStore(id, storeDTO));
    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDTO> moderateStore(@PathVariable Long id, @RequestParam StoreStatus storeStatus){
        return ResponseEntity.ok(storeService.moderateStore(id, storeStatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteStore(@PathVariable Long id){
        storeService.deleteStore(id);
        APIResponse apiResponse = new APIResponse("Store deleted successfully!");
        return ResponseEntity.ok(apiResponse);
    }
}
