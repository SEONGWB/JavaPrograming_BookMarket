package org.example.dto.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartUpdateRequestDto {

    private Long cartItemId;
    private int quantity;

    @Builder
    public CartUpdateRequestDto(Long cartItemId, int quantity) {
        this.cartItemId = cartItemId;
        this.quantity   = quantity;
    }
}