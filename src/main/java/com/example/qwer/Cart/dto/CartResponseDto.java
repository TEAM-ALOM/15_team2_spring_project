package com.example.qwer.cart.dto;

import com.example.qwer.cart.CtoP;
import com.example.qwer.cart.Cart;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CartResponseDto {
    private final Long cartId;
    private final Long userId;
    private final List<CartProductDto> products;

    public CartResponseDto(Cart cart) {
        this.cartId = cart.getCartId();
        this.userId = cart.getUser().getUserId();
        this.products = cart.getProducts().stream()
                .map(CartProductDto::new)
                .collect(Collectors.toList());
    }

    @Getter
    public static class CartProductDto {
        private final Long productId;
        private final Integer quantity;

        public CartProductDto(CtoP ctop) {
            this.productId = ctop.getProduct().getProductId();
            this.quantity = ctop.getQuantity();
        }
    }
}
