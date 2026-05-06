package org.example.dto.cart;

import lombok.Getter;
import org.example.entity.CartItem;

@Getter
public class CartItemResponseDto {

    private Long cartItemId;
    private Long bookId;
    private String title;
    private int price;
    private int quantity;

    public CartItemResponseDto(CartItem cartItem) {
        this.cartItemId = cartItem.getId();
        this.bookId = cartItem.getBook().getId();
        this.title      = cartItem.getBook().getTitle();
        this.price      = cartItem.getBook().getPrice();
        this.quantity   = cartItem.getQuantity();
    }
}