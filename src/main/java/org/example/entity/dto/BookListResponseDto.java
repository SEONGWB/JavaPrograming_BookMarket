package org.example.entity.dto;

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

    public BookListResponseDto(Book entity) {
        this.id = entity.getId();
        this.isbn = entity.getIsbn();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.publisher = entity.getPublisher();
        this.price = entity.getPrice();

        this.category = entity.getCategory();
        this.releaseDate = entity.getReleaseDate();

        this.modifiedDate = entity.getModifiedDate();
    }

}
