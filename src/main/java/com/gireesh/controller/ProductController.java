package com.gireesh.controller;

import com.gireesh.modal.User;
import com.gireesh.payload.dto.ProductDTO;
import com.gireesh.payload.response.APIResponse;
import com.gireesh.service.ProductService;
import com.gireesh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String jwtToken){
        User user = userService.getUserFromJwtToken(jwtToken);
        return ResponseEntity.ok(productService.createProduct(productDTO, user));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDTO>> getProductByStoreId(@PathVariable Long storeId, @RequestHeader("Authorization") String jwtToken){
        return ResponseEntity.ok(productService.getProductsByStoreId(storeId));
    }

    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDTO>> searchByKeyword(@PathVariable Long storeId, @RequestParam String keyword , @RequestHeader("Authorization") String jwtToken){
        return ResponseEntity.ok(productService.searchByKeyword(keyword, storeId));
    }

    @PutMapping("/{productId}")
    public  ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String jwtToken){
        User user = userService.getUserFromJwtToken(jwtToken);
        return ResponseEntity.ok(productService.updatedProduct(productId, productDTO, user));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long productId, @RequestHeader("Authorization") String jwtToken){
        User user = userService.getUserFromJwtToken(jwtToken);
        productService.deleteProduct(productId, user);
        return ResponseEntity.ok(new APIResponse("Product deleted successfully!"));
    }
}
