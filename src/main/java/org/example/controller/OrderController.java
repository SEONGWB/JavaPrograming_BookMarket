package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.order.OrderResponseDto;
import org.example.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/v1/order/{userId}")
    public Long order(@PathVariable Long userId) {
        return orderService.order(userId);
    }

    @GetMapping("/api/v1/orders/{userId}")
    public List<OrderResponseDto> findByUser(@PathVariable Long userId) {
        return orderService.findByUser(userId);
    }

    @GetMapping("/api/v1/orders")
    public List<OrderResponseDto> findAll() {
        return orderService.findAll();
    }
}
