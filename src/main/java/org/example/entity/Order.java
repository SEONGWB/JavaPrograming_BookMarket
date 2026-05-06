package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_tb")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    // 한 명의 사용자는 여러 번 주문할 수 있습니다 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String status; // 주문 상태 (예: ORDERED, CANCEL)

    // 한 번의 주문에는 여러 개의 주문 상품이 포함됩니다 (1:N)
    // 영속성 전이(Cascade)를 설정하여 주문이 저장될 때 상세 항목도 같이 저장되게 합니다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(User user, String status) {
        this.user = user;
        this.status = status;
    }
}