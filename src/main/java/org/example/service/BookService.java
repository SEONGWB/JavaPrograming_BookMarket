package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Book;
import org.example.dto.book.BookListResponseDto;
import org.example.dto.book.BookUpdateRequestDto;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.CartItemRepository;
import org.example.repository.OrderItemRepository;
import org.example.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Long save(BookSaveRequestDto requestDto) {
        User owner = findUser(requestDto.getUserId());
        return bookRepository.save(requestDto.toEntity(owner)).getId();
    }

    @Transactional
    public Long update(Long id, BookUpdateRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다. id=" + id));
        User user = findUser(requestDto.getUserId());
        validateEditable(book, user);
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

    @Transactional
    public void delete(Long id, Long userId) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다. id=" + id));
        User user = findUser(userId);
        validateEditable(book, user);

        if (orderItemRepository.existsByBookId(id)) {
            throw new IllegalArgumentException("주문 내역이 있는 도서는 삭제할 수 없습니다.");
        }

        cartItemRepository.deleteByBookId(id);
        bookRepository.delete(book);
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

    private User findUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인 후 이용해 주세요.");
        }

        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
    }

    private void validateEditable(Book book, User user) {
        if (user.getRole() == Role.ADMIN) {
            return;
        }

        if (book.getOwner() == null || !book.getOwner().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 등록한 도서만 수정하거나 삭제할 수 있습니다.");
        }
    }
}
