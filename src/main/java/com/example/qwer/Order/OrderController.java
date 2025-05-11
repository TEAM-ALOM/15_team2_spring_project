package com.example.qwer.Order;

import com.example.qwer.Order.dto.OrderCancelRequest;
import com.example.qwer.Order.dto.OrderCancelResponse;
import com.example.qwer.Order.dto.OrderCreateRequest;
import com.example.qwer.Order.dto.OrderCreateResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 1. 주문 추가
    @PostMapping
    public OrderCreateResponse createOrder(@RequestBody OrderCreateRequest request) {
        Orders order = orderService.createOrder(request.getUserId());

        OrderCreateResponse createRes = new OrderCreateResponse();
        createRes.setOrderId(order.getId());
        return createRes;
    }


    // 2. 주문 취소
    @DeleteMapping
    public OrderCancelResponse cancelOrder(@RequestBody OrderCancelRequest request) {
        Long userId = orderService.cancelOrder(request.getOrderId());

        OrderCancelResponse cancelRes = new OrderCancelResponse();
        cancelRes.setUserId(userId);
        cancelRes.setOrderId(request.getOrderId());
        return cancelRes;
    }

    // 3. 주문 조회
    @GetMapping
    public List<Orders> getOrders(
            @RequestParam(required = false) Long order_id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate order_date,//ISO 형식 yyyy-MM-dd
            @RequestParam(required = false) Long user_id,
            @RequestParam(required = false) Long product_id
    ) {
        return orderService.getOrders(order_id, order_date, user_id, product_id);
    }
}
