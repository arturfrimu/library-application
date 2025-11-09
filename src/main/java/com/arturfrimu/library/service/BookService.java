package com.arturfrimu.library.service;

import com.arturfrimu.library.dto.request.CreateBookRequest;
import com.arturfrimu.library.dto.response.BookResponse;
import com.arturfrimu.library.entity.Book;
import com.arturfrimu.library.exception.ResourceAlreadyExists;
import com.arturfrimu.library.exception.ResourceNotFound;
import com.arturfrimu.library.mapper.BookMapper;
import com.arturfrimu.library.repository.AuthorRepository;
import com.arturfrimu.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookService {

    BookMapper bookMapper;
    BookRepository bookRepository;
    AuthorRepository authorRepository;

    @Transactional
    public BookResponse createBook(CreateBookRequest request) {
        log.info("Creating book with title: {}", request.title());

        var author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new ResourceNotFound("Author not found with id: %s".formatted(request.authorId())));

        bookRepository.findByIsbn(request.isbn())
                .ifPresent(book -> {
                    throw new ResourceAlreadyExists("Book with ISBN %s already exists".formatted(request.isbn()));
                });

        var book = Book.builder()
                .title(request.title())
                .author(author)
                .isbn(request.isbn())
                .publishedYear(request.publishedYear())
                .build();

        var savedBook = bookRepository.save(book);
        log.info("Book created successfully with id: {}", savedBook.getId());

        return bookMapper.toResponse(savedBook);
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> findBooks(Pageable pageable) {
        log.info("Fetching books with pagination - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());

        return bookRepository.findAll(pageable)
                .map(bookMapper::toResponse);
    }
}

