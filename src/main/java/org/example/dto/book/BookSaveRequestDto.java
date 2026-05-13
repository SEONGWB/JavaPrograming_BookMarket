package org.example.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.entity.Book;
import org.example.entity.User;

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
    private String imageUrl;
    private String category;
    private String releaseDate;
    private Long userId;

    public Book toEntity(User owner) {
        return Book.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .publisher(publisher)
                .price(price)
                .description(description)
                .imageUrl(imageUrl)
                .category(category)
                .releaseDate(releaseDate)
                .owner(owner)
                .build();
    }
}
