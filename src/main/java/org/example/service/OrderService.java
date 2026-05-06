package org.example.service; // 패키지 문 추가

import lombok.RequiredArgsConstructor;
import org.example.dto.cart.CartItemResponseDto;
import org.example.entity.*;
import org.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository; // 추가
    private final CartService cartService;
    private final UserRepository userRepository;
    private final BookRepository bookRepository; // 책 정보를 찾기 위해 추가
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public Long order(Long userId) {
        // 1. 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 없습니다. id=" + userId));

        // 2. 주문 엔티티 생성 (order_tb)
        Orders order = Orders.builder()
                .user(user)
                .orderStatus("ORDER")
                .build();

        ordersRepository.save(order); // ID 생성을 위해 먼저 저장

        // 3. 장바구니 아이템들을 주문 아이템(order_item_tb)으로 변환
        List<CartItemResponseDto> cartItems = cartService.findCartItems(userId);

        for (CartItemResponseDto itemDto : cartItems) {
            // DTO 정보를 바탕으로 실제 Book 엔티티 조회
            Book book = bookRepository.findById(itemDto.getBookId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다."));

            // 주문 항목 생성
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .book(book)
                    .orderPrice(book.getPrice()) // 현재 가격 저장
                    .count(itemDto.getQuantity())
                    .build();

            orderItemRepository.save(orderItem);
        }

        // 4. 장바구니 비우기 (CartService에 이 메서드를 만들어야 합니다)
        // cartService.clearCart(userId);

        return order.getId(); // .id 대신 .getId() 사용
    }

    // CartService.java에 추가할 내용
    @Transactional
    public void clearCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 없습니다."));

        // 장바구니에 담긴 모든 아이템 삭제
        List<CartItem> items = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(items);
    }
}