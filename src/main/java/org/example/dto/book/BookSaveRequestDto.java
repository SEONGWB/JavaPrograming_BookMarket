package org.example.dto.book;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.entity.Book;

@Getter
@NoArgsConstructor
public class BookSaveRequestDto {

    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int price;
    private String description;
    private String category;
    private String releaseDate;

    @Builder
    public BookSaveRequestDto(String isbn, String title, String author, String publisher, int price, String description, String category, String releaseDate) {
        this.isbn           = isbn;
        this.title          = title;
        this.author         = author;
        this.publisher      = publisher;
        this.price          = price;
        this.description    = description;
        this.category       = category;
        this.releaseDate    = releaseDate;
    }

    public Book toEntity() {
        return Book.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .publisher(publisher)
                .price(price)
                .description(description)
                .category(category)
                .releaseDate(releaseDate)
                .build();
    }
}
