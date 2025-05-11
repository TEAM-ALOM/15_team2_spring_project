package com.example.qwer.cart;

import com.example.qwer.cart.dto.CartItemDeleteRequest;
import com.example.qwer.cart.dto.CartRequestDto;
import com.example.qwer.cart.dto.CartResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponseDto> addToCart(@RequestBody CartRequestDto request) {
        return ResponseEntity.ok(new CartResponseDto(cartService.addToCart(request)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(new CartResponseDto(cartService.getCartByUserId(userId)));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items")
    public ResponseEntity<Void> removeItems(@RequestBody CartItemDeleteRequest request) {
        cartService.removeItemsFromCart(request.getUserId(), request.getProductIds());
        return ResponseEntity.noContent().build();
    }

}
