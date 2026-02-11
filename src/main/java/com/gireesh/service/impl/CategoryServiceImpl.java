package com.gireesh.service.impl;

import com.gireesh.domain.UserRole;
import com.gireesh.mapper.CategoryMapper;
import com.gireesh.modal.Category;
import com.gireesh.modal.Store;
import com.gireesh.modal.User;
import com.gireesh.payload.dto.CategoryDTO;
import com.gireesh.repository.CategoryRepository;
import com.gireesh.repository.StoreRepository;
import com.gireesh.service.CategoryService;
import com.gireesh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        User user = userService.getCurrentUser();
        Store store = storeRepository.findById(categoryDTO.getStoreId()).orElseThrow(() -> new RuntimeException("Store not found. " + categoryDTO.getStoreId()));
        Category category = Category.builder()
                .store(store)
                .name(categoryDTO.getName())
                .build();
        checkAuthority(user,category.getStore());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDTO> getCategoriesByStore(Long storeId) {
        return categoryRepository.findByStoreId(storeId).stream().map(CategoryMapper::toDTO).toList();
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category does not exist. " + categoryId));

        User user = userService.getCurrentUser();
        checkAuthority(user,category.getStore());

        category.setName(categoryDTO.getName());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category does not exist. " + categoryId));

        User user = userService.getCurrentUser();
        checkAuthority(user,category.getStore());

        categoryRepository.delete(category);
    }

    private void checkAuthority(User user, Store store){
        boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore = user.equals(store.getStoreAdmin());

        if(!(isAdmin && isSameStore) && !isManager) throw new RuntimeException("User does not have permission to manage category");
    }
}
