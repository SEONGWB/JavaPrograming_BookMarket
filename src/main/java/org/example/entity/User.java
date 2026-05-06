package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_tb")
@Getter @Setter
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 자동으로 생성
public class User {

    @Id // 기본키(PK) 지정
    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(unique = true, length = 100)
    private String email;

    // 참고: 장바구니(Cart)와 1:1 관계를 맺을 예정입니다.
    // @OneToOne(mappedBy = "user")
    // private Cart cart;
}