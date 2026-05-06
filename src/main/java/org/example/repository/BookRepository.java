package org.example.repository;

import org.example.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // 팀원이 추가한 최신순 정렬 기능 유지
    @Query("SELECT p FROM Book p ORDER BY p.id DESC")
    List<Book> findAllDesc();
}