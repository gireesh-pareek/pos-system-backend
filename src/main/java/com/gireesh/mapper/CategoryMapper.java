package com.gireesh.mapper;

import com.gireesh.modal.Category;
import com.gireesh.payload.dto.CategoryDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryMapper {
    public static CategoryDTO toDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .storeId(category.getStore() != null ? category.getStore().getId() : null)
                .build();
    }
}
