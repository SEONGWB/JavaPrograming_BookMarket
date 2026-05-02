package org.example.dto.book;

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
    private String description;
    private String category;
    private String releaseDate;

    @Builder
    public BookUpdateRequestDto(String isbn, String title, String author, String publisher, int price, String description, String category, String releaseDate) {
        this.isbn           = isbn;
        this.title          = title;
        this.author         = author;
        this.publisher      = publisher;
        this.price          = price;
        this.description    = description;
        this.category       = category;
        this.releaseDate    = releaseDate;
    }
}
