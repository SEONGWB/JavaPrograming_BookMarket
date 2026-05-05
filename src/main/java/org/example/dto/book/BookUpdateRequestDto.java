package org.example.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdateRequestDto {

    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int price;
    private String description;
    private String category;
    private String releaseDate;
}
