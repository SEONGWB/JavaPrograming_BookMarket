package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.dto.BookUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

import org.example.service.BookService;
import org.example.entity.dto.BookResponseDto;
import org.example.entity.dto.BookSaveRequestDto;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    @PostMapping("/api/v1/book")
    public Long save(@RequestBody BookSaveRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PutMapping("/api/v1/book/{id}")
    public Long update(@PathVariable Long id, @RequestBody BookUpdateRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @GetMapping("/api/v1/book/{id}")
    public BookResponseDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }
}
