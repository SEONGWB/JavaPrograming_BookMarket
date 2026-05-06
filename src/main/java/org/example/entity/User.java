package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_tb")
@Getter @Setter
@NoArgsConstructor
public class User {

    @Id //
    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(unique = true, length = 100)
    private String email;
}