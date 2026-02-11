package com.gireesh.service;

import com.gireesh.modal.User;
import com.gireesh.payload.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO, User user);
    ProductDTO updatedProduct(Long productId, ProductDTO productDTO, User user);
    void deleteProduct(Long productId, User user);
    List<ProductDTO> getProductsByStoreId(Long storeId);
    List<ProductDTO> searchByKeyword(String keyword, Long storeId);

}
