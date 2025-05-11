package com.example.qwer.orders;

import com.example.qwer.orders.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 장바구니 기반 자동 주문 생성
    @PostMapping
    public OrderCreateResponse createOrderFromCart(@RequestParam Long userId) {
        Orders order = orderService.createOrderFromCart(userId);

        OrderCreateResponse res = new OrderCreateResponse();
        res.setOrderId(order.getOrderId());
        return res;
    }

    // 주문 취소
    @DeleteMapping
    public OrderCancelResponse cancelOrder(@RequestBody OrderCancelRequest request) {
        Long userId = orderService.cancelOrder(request.getOrderId());

        OrderCancelResponse res = new OrderCancelResponse();
        res.setUserId(userId);
        res.setOrderId(request.getOrderId());
        return res;
    }

    // 주문 조회
    @GetMapping
    public List<OrderResponseDto> getOrders(
            @RequestParam(required = false) Long order_id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate order_date,
            @RequestParam(required = false) Long user_id,
            @RequestParam(required = false) Long product_id
    ) {
        return orderService.getOrders(order_id, order_date, user_id, product_id)
                .stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

}
