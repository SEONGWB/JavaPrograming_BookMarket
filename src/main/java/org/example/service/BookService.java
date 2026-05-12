package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Book;
import org.example.dto.book.BookListResponseDto;
import org.example.dto.book.BookUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.example.repository.BookRepository;
import org.example.dto.book.BookResponseDto;
import org.example.dto.book.BookSaveRequestDto;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Long save(BookSaveRequestDto requestDto) {
        return bookRepository.save(requestDto.toEntity()).getId();
    }

    public Long update(Long id, BookUpdateRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다. id=" + id));
        book.update(
                requestDto.getIsbn(),
                requestDto.getTitle(),
                requestDto.getAuthor(),
                requestDto.getPublisher(),
                requestDto.getPrice(),
                requestDto.getDescription(),
                requestDto.getImageUrl(),
                requestDto.getCategory(),
                requestDto.getReleaseDate()
        );
        return id;
    }

    @Transactional(readOnly = true)
    public BookResponseDto findById(Long id) {
        Book entity = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다. id=" + id));
        return new BookResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<BookListResponseDto> findAllDesc() {
        return bookRepository.findAllDesc().stream()
                .map(BookListResponseDto::new)
                .collect(Collectors.toList());
    }
}
