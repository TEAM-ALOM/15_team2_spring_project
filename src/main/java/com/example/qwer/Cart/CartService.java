package com.example.qwer.cart;

import com.example.qwer.cart.dto.CartRequestDto;
import com.example.qwer.product.Product;
import com.example.qwer.product.ProductRepository;
import com.example.qwer.user.User;
import com.example.qwer.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CtoPRepository ctoPRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 장바구니에 상품 추가 (User 당 하나의 Cart 유지)
    @Transactional
    public Cart addToCart(CartRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음: " + dto.getUserId()));

        // 기존 장바구니 있는지 확인
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null) {
            cart = Cart.builder()
                    .user(user)
                    .products(new ArrayList<>())
                    .build();
            cart = cartRepository.save(cart);
        }

        List<CtoP> toAdd = new ArrayList<>();
        for (CartRequestDto.CartItem item : dto.getProducts()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품 없음: " + item.getProductId()));

            toAdd.add(CtoP.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(item.getQuantity())
                    .build());
        }

        ctoPRepository.saveAll(toAdd);
        cart.getProducts().addAll(toAdd);  // 연관관계 유지
        return cart;
    }

    // 사용자 ID로 장바구니 조회
    public Cart getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음: " + userId));

        return cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("해당 유저의 장바구니 없음"));
    }

    // 장바구니에서 특정 상품들 제거
    @Transactional
    public void removeItemsFromCart(Long userId, List<Long> productIds) {
        Cart cart = getCartByUserId(userId);

        // 삭제할 항목 필터링
        List<CtoP> toRemove = cart.getProducts().stream()
                .filter(ctop -> productIds.contains(ctop.getProduct().getProductId()))
                .collect(Collectors.toList());

        ctoPRepository.deleteAll(toRemove);

        // 남은 항목으로 장바구니 업데이트
        List<CtoP> remaining = cart.getProducts().stream()
                .filter(ctop -> !productIds.contains(ctop.getProduct().getProductId()))
                .collect(Collectors.toList());

        cart.setProducts(new ArrayList<>(remaining));
        cartRepository.save(cart);
    }

    // 장바구니 자체 삭제
    public void clearCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
