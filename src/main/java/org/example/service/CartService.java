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
import java.util.Optional;
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

        //해당 장바구니에 이미 이 책이 담겨 있는지 확인
        Optional<CartItem> itemOptional = cartItemRepository.findByCartIdAndBookId(cart.getId(), book.getId());

        if (itemOptional.isPresent()) {
            //이미 있다면: 기존 수량에 더하기 (Dirty Checking으로 자동 반영)
            CartItem existingItem = itemOptional.get();
            existingItem.setQuantity(existingItem.getQuantity() + requestDto.getQuantity());
            return existingItem.getId();
        } else {
            // 4-2. 없다면: 새롭게 생성하여 저장
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .book(book)
                    .quantity(requestDto.getQuantity())
                    .build();
            return cartItemRepository.save(newItem).getId();
        }
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

    @Transactional
    public void clearCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 없습니다."));

        List<CartItem> items = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(items); // 여기서 에러가 났다면 필드 선언을 확인하세요!
    }
}