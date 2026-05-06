package org.example.entity;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_tb")
public class Book extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 13, nullable = false)  private String isbn;
    @Column(length = 250, nullable = false) private String title;
    @Column(length = 100, nullable = false) private String author;
    @Column(length = 100, nullable = false) private String publisher;
                                            private int price;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String category;
    private String releaseDate;

    @Builder
    public Book(String isbn, String title, String author, String publisher, int price, String description, String category, String releaseDate) {
        this.isbn        = isbn;
        this.title       = title;
        this.author      = author;
        this.publisher   = publisher;
        this.price       = price;
        this.description = description;
        this.category    = category;
        this.releaseDate = releaseDate;
    }

    public void update(String isbn, String title, String author, String publisher, int price, String description, String category, String releaseDate) {
        this.isbn        = isbn;
        this.title       = title;
        this.author      = author;
        this.publisher   = publisher;
        this.price       = price;
        this.description = description;
        this.category    = category;
        this.releaseDate = releaseDate;
    }
}
