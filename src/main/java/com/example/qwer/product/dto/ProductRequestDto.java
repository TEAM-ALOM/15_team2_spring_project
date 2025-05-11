package com.example.qwer.product.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 상품 생성 요청을 위한 DTO
 */
@Getter
@Setter
public class ProductRequestDto {
    private String name;
    private Integer price;
    private String category;
}
