package org.example.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookUpdateRequestDto {

    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int price;

    @Builder
    public BookUpdateRequestDto(String isbn, String title, String author, String publisher, int price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
    }
}
