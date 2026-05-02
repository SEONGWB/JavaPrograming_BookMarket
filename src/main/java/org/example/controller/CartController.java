package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.cart.CartAddRequestDto;
import org.example.dto.cart.CartItemResponseDto;
import org.example.dto.cart.CartUpdateRequestDto;
import org.example.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping("/api/v1/cart/{userId}")
    public Long createCart(@PathVariable Long userId) {
        return cartService.createCart(userId);
    }

    @PostMapping("/api/v1/cart/{userId}/book")
    public Long addBook(@PathVariable Long userId, @RequestBody CartAddRequestDto requestDto) {
        return cartService.addBook(userId, requestDto);
    }

    @GetMapping("/api/v1/cart/{userId}")
    public List<CartItemResponseDto> findCartItems(@PathVariable Long userId) {
        return cartService.findCartItems(userId);
    }

    @PutMapping("/api/v1/cart")
    public void updateQuantity(@RequestBody CartUpdateRequestDto requestDto) {
        cartService.updateQuantity(requestDto);
    }

    @DeleteMapping("/api/v1/cart/{cartItemId}")
    public void deleteItem(@PathVariable Long cartItemId) {
        cartService.deleteItem(cartItemId);
    }
}