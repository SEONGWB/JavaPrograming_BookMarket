package org.example.dto.order;

import lombok.Getter;
import org.example.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponseDto {

    private Long id;
    private Long userId;
    private String userName;
    private String status;
    private LocalDateTime orderDate;
    private List<OrderItemResponseDto> items;
    private int totalPrice;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.userName = order.getUser().getName();
        this.status = order.getStatus();
        this.orderDate = order.getCreatedDate();
        this.items = order.getOrderItems().stream()
                .map(OrderItemResponseDto::new)
                .collect(Collectors.toList());
        this.totalPrice = items.stream()
                .mapToInt(OrderItemResponseDto::getTotalPrice)
                .sum();
    }
}
