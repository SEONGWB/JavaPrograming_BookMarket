package org.example.dto.book;

import lombok.Getter;
import java.time.LocalDateTime;

import lombok.NoArgsConstructor;
import org.example.entity.Book;

@Getter
@NoArgsConstructor
public class BookListResponseDto {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int price;
    private String imageUrl;

    private String category;
    private String releaseDate;
    private Long ownerId;
    private String ownerName;

    private LocalDateTime modifiedDate;

    public BookListResponseDto(Book book) {
        this.id             = book.getId();
        this.isbn           = book.getIsbn();
        this.title          = book.getTitle();
        this.author         = book.getAuthor();
        this.publisher      = book.getPublisher();
        this.price          = book.getPrice();
        this.imageUrl       = book.getImageUrl();
        this.category       = book.getCategory();
        this.releaseDate    = book.getReleaseDate();
        if (book.getOwner() != null) {
            this.ownerId     = book.getOwner().getId();
            this.ownerName   = book.getOwner().getName();
        }
        this.modifiedDate   = book.getModifiedDate();
    }
}
