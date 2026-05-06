package org.example.dto.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartAddRequestDto {

    private Long bookId;
    private int quantity;

    @Builder
    public CartAddRequestDto(Long bookId, int quantity) {
        this.bookId   = bookId;
        this.quantity = quantity;
    }
}