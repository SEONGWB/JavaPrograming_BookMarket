package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 13, nullable = false)  private String isbn;
    @Column(length = 250, nullable = false) private String title;
    @Column(length = 100, nullable = false) private String author;
    @Column(length = 100, nullable = false) private String publisher;
                                            private int price;

    @Builder
    public Book(String isbn, String title, String author, String publisher, int price) {
        this.isbn       = isbn;
        this.title      = title;
        this.author     = author;
        this.publisher  = publisher;
        this.price      = price;
    }

    public void update(String isbn, String title, String author, String publisher, int price) {
        this.isbn       = isbn;
        this.title      = title;
        this.author     = author;
        this.publisher  = publisher;
        this.price      = price;
    }
}
