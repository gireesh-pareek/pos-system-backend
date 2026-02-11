package com.gireesh.service.impl;

import com.gireesh.mapper.ProductMapper;
import com.gireesh.modal.Category;
import com.gireesh.modal.Product;
import com.gireesh.modal.Store;
import com.gireesh.modal.User;
import com.gireesh.payload.dto.ProductDTO;
import com.gireesh.repository.CategoryRepository;
import com.gireesh.repository.ProductRepository;
import com.gireesh.repository.StoreRepository;
import com.gireesh.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, User user) {
        Store store = storeRepository.findById(productDTO.getStoreId()).orElseThrow(() -> new RuntimeException("Store not found"));

        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found!" + productDTO.getCategoryId()));
        Product product = ProductMapper.toEntity(productDTO, store, category);

        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updatedProduct(Long productId, ProductDTO productDTO, User user) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        if(productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found. " + productDTO.getCategoryId()));
            product.setCategory(category);
        }

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setSku(productDTO.getSku());
        product.setImageUrl(productDTO.getImageUrl());
        product.setMrp(productDTO.getMrp());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setBrand(product.getBrand());
        product.setUpdatedAt(LocalDateTime.now());

        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long productId, User user) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found."));

        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getProductsByStoreId(Long storeId) {
        return productRepository.findByStoreId(storeId).stream().map(ProductMapper::toDTO).toList();
    }

    @Override
    public List<ProductDTO> searchByKeyword(String keyword, Long storeId) {
        return productRepository.searchByKeyword(storeId, keyword).stream().map(ProductMapper::toDTO).toList();
    }
}
