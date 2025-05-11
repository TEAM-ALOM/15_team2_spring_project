package com.example.qwer.orders.dto;

import com.example.qwer.orders.Orders;
import com.example.qwer.orders.OtoP;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private LocalDateTime createdAt;
    private List<OrderProductDto> products;

    public OrderResponseDto(Orders order) {
        this.orderId = order.getOrderId();
        this.userId = order.getUser().getUserId();
        this.createdAt = order.getCreatedAt();
        this.products = order.getProducts().stream()
                .map(OrderProductDto::new)
                .collect(Collectors.toList());
    }

    @Getter @Setter
    public static class OrderProductDto {
        private Long productId;
        private String name;
        private Integer quantity;

        public OrderProductDto(OtoP otoP) {
            this.productId = otoP.getProduct().getProductId();
            this.name = otoP.getProduct().getName(); // assuming Product has getName()
            this.quantity = otoP.getQuantity();
        }
    }
}
