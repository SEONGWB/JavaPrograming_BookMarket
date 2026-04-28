package org.example.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.example.entity.Book;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSaveRequestDto {

    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int price;

    @Builder
    public BookSaveRequestDto(String title, String content, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
    }

    public Book toEntity() {
        return Book.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .publisher(publisher)
                .price(price)
                .build();
    }
}
