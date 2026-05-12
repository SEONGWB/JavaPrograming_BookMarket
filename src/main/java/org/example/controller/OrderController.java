package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/v1/order/{userId}")
    public Long order(@PathVariable Long userId) {
        return orderService.order(userId);
    }
}