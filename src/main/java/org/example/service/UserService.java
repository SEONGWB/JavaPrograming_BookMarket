package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.user.LoginRequestDto;
import org.example.dto.user.SignupRequestDto;
import org.example.dto.user.UserResponseDto;
import org.example.dto.user.UserUpdateRequestDto;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long signup(SignupRequestDto requestDto) {
        userRepository.findByLoginId(requestDto.getLoginId()).ifPresent(user -> {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");});
        return userRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public UserResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByLoginId(requestDto.getLoginId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (user.getPassword() == null || !user.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto update(Long id, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        user.updateProfile(requestDto.getName(), requestDto.getPassword());
        return new UserResponseDto(user);
    }
}
