package org.example.entity.dto;

import lombok.Getter;

import org.example.entity.Book;

@Getter
public class BookResponseDto {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int price;

    public BookResponseDto(Book entity) {
        this.id = entity.getId();
        this.isbn = entity.getIsbn();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.publisher = entity.getPublisher();
        this.price = entity.getPrice();
    }
}