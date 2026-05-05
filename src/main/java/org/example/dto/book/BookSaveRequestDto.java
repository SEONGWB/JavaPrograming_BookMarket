package org.example.dto.book;

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
    private String description;
    private String category;
    private String releaseDate;

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
