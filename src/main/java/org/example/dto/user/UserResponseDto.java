package org.example.dto.user;

import lombok.Getter;
import org.example.entity.User;

@Getter
public class UserResponseDto {

    private Long   id;
    private String loginId;
    private String name;
    private String provider;
    private String role;

    public UserResponseDto(User user) {
        this.id       = user.getId();
        this.loginId  = user.getLoginId();
        this.name     = user.getName();
        this.provider = user.getProvider();
        this.role     = user.getRole() == null ? "USER" : user.getRole().name();
    }
}
