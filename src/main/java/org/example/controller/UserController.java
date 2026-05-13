package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.user.LoginRequestDto;
import org.example.dto.user.SignupRequestDto;
import org.example.dto.user.UserResponseDto;
import org.example.dto.user.UserUpdateRequestDto;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/signup")
    public Long signup(@RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("/api/v1/login")
    public UserResponseDto login(@RequestBody LoginRequestDto requestDto) {
        return userService.login(requestDto);
    }

    @GetMapping("/api/v1/user/{id}")
    public UserResponseDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/api/v1/users")
    public List<UserResponseDto> findAll() {
        return userService.findAll();
    }

    @PutMapping("/api/v1/user/{id}")
    public UserResponseDto update(@PathVariable Long id, @RequestBody UserUpdateRequestDto requestDto) {
        return userService.update(id, requestDto);
    }
}
