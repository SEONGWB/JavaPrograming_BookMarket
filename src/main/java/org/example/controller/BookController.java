package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.book.BookListResponseDto;
import org.example.dto.book.BookResponseDto;
import org.example.dto.book.BookSaveRequestDto;
import org.example.dto.book.BookUpdateRequestDto;
import org.example.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/api/v1/book")
    public Long save(@RequestBody BookSaveRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PutMapping("/api/v1/book/{id}")
    public Long update(@PathVariable Long id,
                       @RequestBody BookUpdateRequestDto requestDto) {

        return bookService.update(id, requestDto);
    }

    @GetMapping("/api/v1/book/{id}")
    public BookResponseDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/api/v1/books")
    public List<BookListResponseDto> findAll() {
        return bookService.findAllDesc();
    }

    @DeleteMapping("/api/v1/book/{id}")
    public Long delete(@PathVariable Long id, @RequestParam Long userId) {
        bookService.delete(id, userId);
        return id;
    }
}
