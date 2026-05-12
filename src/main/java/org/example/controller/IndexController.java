package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.cart.CartItemResponseDto;
import org.example.service.BookService;
import org.example.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final BookService bookService;
    private final CartService cartService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("book", bookService.findAllDesc());
        return "books";
    }

    @GetMapping("/book/save")
    public String bookSave() {
        return "book-save";
    }

    @GetMapping("/book/update/{id}")
    public String bookUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "book-update";
    }

    @GetMapping("/cart/{userId}")
    public String cart(@PathVariable Long userId, Model model) {
        List<CartItemResponseDto> cartItems;
        try {
            cartItems = cartService.findCartItems(userId);
        } catch (IllegalArgumentException e) {
            cartItems = Collections.emptyList();
            model.addAttribute("cartMessage", "장바구니가 아직 준비되지 않았습니다. 회원가입 후 다시 이용해 주세요.");
        }

        int totalPrice = cartItems.stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("userId", userId);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/oauth/success")
    public String oauthSuccess() {
        return "oauth-success";
    }
}
