package com.example.qwer.Order;

import com.example.qwer.User;
import com.example.qwer.UserRepository;
import com.example.qwer.tmp.*;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class OrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderProductRepository = orderProductRepository;
    }

    // 1. 주문 생성 (장바구니 기반)

    public Orders createOrder(Long userId) {
        //해당 유저가 존재하는지 찾기. 없으면 에러
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        //장바구니 상품 리스트에 저장
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        
        //장바구니에 상품이 담겨있는지 확인. 비어있으면 에러
        if (cartItems.isEmpty())
            throw new RuntimeException("장바구니가 비어 있습니다.");

        //주문 정보 입력
        Orders order = new Orders();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        //주문한 상품 목록 OtoP에 전달
        for (Cart cartItem : cartItems) {
            Product product = cartItem.getProduct();

            OrderProduct op = new OrderProduct();
            op.setOrder(order);
            op.setProduct(product);

            orderProductRepository.save(op);
        }

        // 주문 후 장바구니 비움
        cartRepository.deleteAll(cartItems);

        return orderRepository.save(order);
    }

    // 2. 주문 취소

    public Long cancelOrder(Long orderId) {
        //해당 주문 정보 있는지 확인. 없으면 에러
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문이 존재하지 않습니다."));

        Long userId = order.getUser().getId(); // 응답용으로 추출
        orderRepository.delete(order);//cascade로 orderProducts 자동 삭제됨

        return userId;
    }

    // 3. 주문 조회 (AND 조건 필터링)

    public List<Orders> getOrders(Long orderId, LocalDate orderDate, Long userId, Long productId) {
        List<Orders> allOrders = orderRepository.findAll();
        List<Orders> filteredOrders = new ArrayList<>();

        for (Orders order : allOrders) {
            boolean match = true;

            // 조건 1: orderId가 지정되어 있으면, 해당 주문의 ID와 비교
            if (orderId != null && !order.getId().equals(orderId)) {
                match = false;
            }

            // 조건 2: orderDate가 지정되어 있으면, 생성일의 날짜 부분과 비교
            if (orderDate != null && !order.getCreatedAt().toLocalDate().equals(orderDate)) {
                match = false;
            }

            // 조건 3: userId가 지정되어 있으면, 주문한 유저의 ID와 비교
            if (userId != null && !order.getUser().getId().equals(userId)) {
                match = false;
            }

            // 조건 4: productId가 지정된 경우, 해당 주문의 상품 목록을 별도 조회
            if (productId != null) {
                List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(order.getId());
                boolean productMatch = false;
                for (OrderProduct op : orderProducts) {
                    if (op.getProduct().getId().equals(productId)) {
                        productMatch = true;
                        break;
                    }
                }
                if (!productMatch) {
                    match = false;
                }
            }

            if (match) {
                filteredOrders.add(order);
            }
        }

        return filteredOrders;
    }

}

