package org.example.dto.book;

import lombok.Getter;
import java.time.LocalDateTime;

import org.example.entity.Book;

@Getter
public class BookListResponseDto {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int price;

    private String category;
    private String releaseDate;

    private LocalDateTime modifiedDate;

    public BookListResponseDto(Book book) {
        this.id             = book.getId();
        this.isbn           = book.getIsbn();
        this.title          = book.getTitle();
        this.author         = book.getAuthor();
        this.publisher      = book.getPublisher();
        this.price          = book.getPrice();
        this.category       = book.getCategory();
        this.releaseDate    = book.getReleaseDate();
        this.modifiedDate   = book.getModifiedDate();
    }
}
