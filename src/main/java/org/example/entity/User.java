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
}