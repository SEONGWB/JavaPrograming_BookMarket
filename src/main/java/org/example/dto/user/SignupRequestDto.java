package org.example.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.entity.Role;
import org.example.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequestDto {

    private String loginId;
    private String password;
    private String name;

    public User toEntity() {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .role(Role.USER)
                .provider("local")
                .build();
    }
}