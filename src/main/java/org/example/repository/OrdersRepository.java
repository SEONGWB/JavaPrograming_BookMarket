package org.example.repository;

import org.example.entity.Orders;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    // 특정 사용자의 주문 내역을 최신순으로 보고 싶을 때를 대비해 미리 만들어둠.
    List<Orders> findByUserOrderByCreatedDateDesc(User user);
}
