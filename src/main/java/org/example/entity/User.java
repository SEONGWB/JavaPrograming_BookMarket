package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_tb")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)   
    private String loginId;
  
    @Column(nullable = true)   
    private String password;
  
    @Column(nullable = false, length = 30)                  
    private String name;
  
    @Enumerated(EnumType.STRING)                            
    private Role role;
  
    @Column(nullable = false)                               
    private String provider;

    public void updateProfile(String name, String password) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름을 입력해 주세요.");
        }

        this.name = name;
        if (password != null && !password.isBlank()) {
            this.password = password;
        }
    }
}
