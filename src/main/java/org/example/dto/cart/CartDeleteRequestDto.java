package org.example.dto.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CartDeleteRequestDto {

    private List<Long> cartItemIds;

    @Builder
    public CartDeleteRequestDto(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }
}