package com.example.qwer.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartItemDeleteRequest {
    private Long userId;
    private List<Long> productIds;
}
