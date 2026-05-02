package org.example.dto.book;

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
    private String description;
    private String category;
    private String releaseDate;

    public BookResponseDto(Book book) {
        this.id             = book.getId();
        this.isbn           = book.getIsbn();
        this.title          = book.getTitle();
        this.author         = book.getAuthor();
        this.publisher      = book.getPublisher();
        this.price          = book.getPrice();
        this.description    = book.getDescription();
        this.category       = book.getCategory();
        this.releaseDate    = book.getReleaseDate();
    }
}