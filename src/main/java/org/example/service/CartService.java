package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.cart.CartAddRequestDto;
import org.example.dto.cart.CartItemResponseDto;
import org.example.dto.cart.CartUpdateRequestDto;
import org.example.entity.Book;
import org.example.entity.Cart;
import org.example.entity.CartItem;
import org.example.entity.User;
import org.example.repository.BookRepository;
import org.example.repository.CartItemRepository;
import org.example.repository.CartRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Long createCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        Cart cart = Cart.builder()
                .user(user)
                .build();

        return cartRepository.save(cart).getId();
    }

    @Transactional
    public Long addBook(Long userId, CartAddRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("장바구니가 없습니다."));
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(() -> new IllegalArgumentException("책이 없습니다."));
        CartItem item = CartItem.builder()
                .cart(cart)
                .book(book)
                .quantity(requestDto.getQuantity())
                .build();
        return cartItemRepository.save(item).getId();
    }

    @Transactional(readOnly = true)
    public List<CartItemResponseDto> findCartItems(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("장바구니가 없습니다."));
        return cartItemRepository.findByCart(cart).stream().map(CartItemResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void updateQuantity(CartUpdateRequestDto requestDto) {
        CartItem item = cartItemRepository.findById(requestDto.getCartItemId()).orElseThrow(() -> new IllegalArgumentException("장바구니 상품이 없습니다."));
        item.setQuantity(requestDto.getQuantity());
    }

    @Transactional
    public void deleteItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}