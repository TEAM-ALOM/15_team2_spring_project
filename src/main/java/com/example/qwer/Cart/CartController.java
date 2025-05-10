package com.example.qwer.Cart;

import com.example.qwer.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestParam Long userId, @RequestParam Long productId) {
        cartService.addProductToCart(userId, productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Set<Product>> getCartItems(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProducts(@RequestParam Long userId, @RequestBody Set<Long> productIds) {
        cartService.removeProductsFromCart(userId, productIds);
        return ResponseEntity.ok().build();
    }
}
