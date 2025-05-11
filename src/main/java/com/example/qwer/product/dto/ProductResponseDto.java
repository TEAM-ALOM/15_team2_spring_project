package com.example.qwer.product.dto;

import com.example.qwer.product.Product;
import lombok.Getter;

/**
 * 상품 응답을 위한 DTO
 */
@Getter
public class ProductResponseDto {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String category;

    public ProductResponseDto(Product product) {
        this.id = product.getProductId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.category = product.getCategory() != null ? product.getCategory() : null;
    }
}
