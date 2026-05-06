package org.example.repository;

import org.example.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT p FROM Book p ORDER BY p.id DESC")
    List<Book> findAllDesc();

    List<Book> findByTitleContaining(String keyword);

    List<Book> findByAuthorContaining(String keyword);
}