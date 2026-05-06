package org.example.repository;

import org.example.entity.Order;
import org.example.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // 특정 주문서에 포함된 모든 상품 항목들을 찾을 때 사용함.
    List<OrderItem> findByOrder(Order order);
}