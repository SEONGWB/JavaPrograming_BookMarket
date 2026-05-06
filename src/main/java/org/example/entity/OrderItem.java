package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_item_tb") // 과제 요구사항 준수
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int orderPrice;
    private int count;

    @Builder
    public OrderItem(Order order, Book book, int orderPrice, int count) {
        this.order = order;
        this.book = book;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}