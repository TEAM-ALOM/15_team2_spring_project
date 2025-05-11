package com.example.qwer.orders;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtoPRepository extends JpaRepository<OtoP, Long> {
    List<OtoP> findByOrders_OrderId(Long orderId);  // 주문 ID로 주문 상품들 조회
}
