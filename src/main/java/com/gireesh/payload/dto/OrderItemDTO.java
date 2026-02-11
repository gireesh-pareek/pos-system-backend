package com.gireesh.payload.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    Long orderItemId;

    @NotNull
    @Min(1)
    private Integer quantity;

    private Double price;

    private Long productId;

    private ProductDTO product;

    private Long orderId;
}
