package com.example.qwer.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartRequestDto {
    private Long userId;
    private List<CartItem> products;

    @Getter @Setter
    public static class CartItem {
        private Long productId;
        private Integer quantity;
    }
}
