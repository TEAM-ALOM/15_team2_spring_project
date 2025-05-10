package com.example.qwer.Cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addProductToCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));
                    Cart newCart = Cart.builder()
                            .user(user)
                            .products(new java.util.HashSet<>())
                            .build();
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        cart.getProducts().add(product);
    }

    @Transactional(readOnly = true)
    public Set<Product> getCartItems(Long userId) {
        return cartRepository.findByUserId(userId)
                .map(Cart::getProducts)
                .orElse(Set.of());
    }

    @Transactional
    public void removeProductsFromCart(Long userId, Set<Long> productIds) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        Set<Product> products = cart.getProducts();
        products.removeIf(product -> productIds.contains(product.getId()));
    }
}
