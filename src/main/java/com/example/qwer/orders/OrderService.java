package com.example.qwer.orders;

import com.example.qwer.cart.Cart;
import com.example.qwer.cart.CartRepository;
import com.example.qwer.cart.CtoP;
import com.example.qwer.cart.CtoPRepository;
import com.example.qwer.product.Product;
import com.example.qwer.user.User;
import com.example.qwer.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CtoPRepository ctopRepository;
    private final OtoPRepository otopRepository;

    /**
     * 장바구니 기반 주문 생성 (주문 생성 후 장바구니 비움)
     */
    @Transactional
    public Orders createOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("장바구니 없음"));

        if (cart.getProducts().isEmpty()) {
            throw new RuntimeException("장바구니가 비어 있습니다.");
        }

        Orders order = Orders.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        for (CtoP cartItem : cart.getProducts()) {
            Product product = cartItem.getProduct();

            OtoP orderProduct = OtoP.builder()
                    .orders(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .build();

            otopRepository.save(orderProduct);
        }

        // 장바구니 비우기
        ctopRepository.deleteAll(cart.getProducts());
        cart.setProducts(new ArrayList<>());
        cartRepository.save(cart);

        return order;
    }

    /**
     * 장바구니 기반 주문 생성 (리팩토링 버전)
     */
    @Transactional
    public Orders createOrderFromCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("해당 유저의 장바구니 없음"));

        List<CtoP> items = cart.getProducts();
        if (items.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어 있습니다.");
        }

        Orders order = Orders.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        List<OtoP> orderItems = new ArrayList<>();
        for (CtoP item : items) {
            orderItems.add(OtoP.builder()
                    .orders(order)
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .build());
        }

        otopRepository.saveAll(orderItems);
        order.setProducts(orderItems); // 연관 매핑 되어 있다면 유용

        // 장바구니 비우기
        ctopRepository.deleteAll(items);
        cart.setProducts(new ArrayList<>());
        cartRepository.save(cart);

        return order;
    }

    /**
     * 주문 취소
     */
    @Transactional
    public Long cancelOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문이 존재하지 않습니다."));
        Long userId = order.getUser().getUserId();
        orderRepository.delete(order);
        return userId;
    }

    /**
     * 주문 조회 (조건 필터링)
     */
    public List<Orders> getOrders(Long orderId, LocalDate orderDate, Long userId, Long productId) {
        List<Orders> allOrders = orderRepository.findAll();
        List<Orders> filteredOrders = new ArrayList<>();

        for (Orders order : allOrders) {
            boolean match = true;

            if (orderId != null && !order.getOrderId().equals(orderId)) match = false;
            if (orderDate != null && !order.getCreatedAt().toLocalDate().equals(orderDate)) match = false;
            if (userId != null && !order.getUser().getUserId().equals(userId)) match = false;

            if (productId != null) {
                List<OtoP> otops = otopRepository.findByOrders_OrderId(order.getOrderId());
                boolean productMatch = otops.stream()
                        .anyMatch(op -> op.getProduct().getProductId().equals(productId));
                if (!productMatch) match = false;
            }

            if (match) filteredOrders.add(order);
        }

        return filteredOrders;
    }
}
