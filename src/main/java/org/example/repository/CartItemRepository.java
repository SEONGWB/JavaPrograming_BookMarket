package org.example.repository;

import org.example.entity.CartItem;
import org.example.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);
    Optional<CartItem> findByCartIdAndBookId(Long cartId, Long bookId);
    void deleteByBookId(Long bookId);
}
