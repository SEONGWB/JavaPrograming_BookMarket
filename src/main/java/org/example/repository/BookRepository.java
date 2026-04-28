package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import org.example.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

    @Query("SELECT p FROM Book p ORDER BY p.id DESC")
    List<Book> findAllDesc();
}

