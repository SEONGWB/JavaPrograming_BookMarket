package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item_tb")
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    // 어떤 주문에 속한 항목인지 연결 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // 어떤 책을 주문한 것인지 연결 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int orderPrice; // 주문 당시의 가격 (할인 등이 있을 수 있으므로 따로 기록)
    private int count;      // 주문 수량

    @Builder
    public OrderItem(Order order, Book book, int orderPrice, int count) {
        this.order = order;
        this.book = book;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}