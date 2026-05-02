package org.example.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.entity.Role;
import org.example.entity.User;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    private String loginId;
    private String password;
    private String name;

    @Builder
    public SignupRequestDto(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }

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