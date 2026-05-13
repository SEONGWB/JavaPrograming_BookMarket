package org.example.dto.order;

import lombok.Getter;
import org.example.entity.OrderItem;

@Getter
public class OrderItemResponseDto {

    private Long bookId;
    private String title;
    private int orderPrice;
    private int count;
    private int totalPrice;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.bookId = orderItem.getBook().getId();
        this.title = orderItem.getBook().getTitle();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
        this.totalPrice = orderPrice * count;
    }
}
