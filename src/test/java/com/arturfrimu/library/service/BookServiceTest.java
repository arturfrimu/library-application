package com.arturfrimu.library.service;

import com.arturfrimu.library.dto.request.CreateBookRequest;
import com.arturfrimu.library.dto.response.BookResponse;
import com.arturfrimu.library.entity.Author;
import com.arturfrimu.library.entity.Book;
import com.arturfrimu.library.exception.ResourceAlreadyExists;
import com.arturfrimu.library.exception.ResourceNotFound;
import com.arturfrimu.library.mapper.BookMapper;
import com.arturfrimu.library.repository.AuthorRepository;
import com.arturfrimu.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookMapper bookMapper;
    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    BookService bookService;

    @Test
    void shouldCreateBook() {
        var authorId = UUID.randomUUID();
        var bookId = UUID.randomUUID();
        var request = new CreateBookRequest("Test Book", authorId, "9780123456789", 2024);

        var author = Author.builder()
                .name("Test Author")
                .build();
        author.setId(authorId);

        var savedBook = Book.builder()
                .title(request.title())
                .author(author)
                .isbn(request.isbn())
                .publishedYear(request.publishedYear())
                .build();
        savedBook.setId(bookId);

        var now = Instant.now();
        var bookResponse = new BookResponse(
                bookId,
                request.title(),
                authorId,
                author.getName(),
                request.isbn(),
                request.publishedYear(),
                true,
                now,
                now
        );

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.findByIsbn(request.isbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(bookMapper.toResponse(savedBook)).thenReturn(bookResponse);

        var result = bookService.createBook(request);

        assertNotNull(result, "Result must not be null");
        assertEquals(bookId, result.id(), "Book ID must match");
        assertEquals(request.title(), result.title(), "Title must match");
        assertEquals(request.isbn(), result.isbn(), "ISBN must match");
        assertEquals(request.publishedYear(), result.publishedYear(), "Published year must match");
        assertEquals(authorId, result.authorId(), "Author ID must match");

        verify(authorRepository).findById(authorId);
        verify(bookRepository).findByIsbn(request.isbn());
        verify(bookRepository).save(any(Book.class));
        verify(bookMapper).toResponse(savedBook);
    }

    @Test
    void shouldThrowResourceNotFoundWhenAuthorDoesNotExist() {
        var authorId = UUID.randomUUID();
        var request = new CreateBookRequest("Test Book", authorId, "9780123456789", 2024);

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFound.class, () -> bookService.createBook(request));

        assertEquals("Author not found with id: %s".formatted(authorId), exception.getMessage());
        verify(authorRepository).findById(authorId);
        verify(bookRepository, never()).findByIsbn(anyString());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void shouldThrowResourceAlreadyExistsWhenIsbnExists() {
        var authorId = UUID.randomUUID();
        var bookId = UUID.randomUUID();
        var request = new CreateBookRequest("Test Book", authorId, "9780123456789", 2024);

        var author = Author.builder()
                .name("Test Author")
                .build();
        author.setId(authorId);

        var existingBook = Book.builder()
                .title("Existing Book")
                .author(author)
                .isbn(request.isbn())
                .publishedYear(2023)
                .build();
        existingBook.setId(bookId);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.findByIsbn(request.isbn())).thenReturn(Optional.of(existingBook));

        var exception = assertThrows(ResourceAlreadyExists.class, () -> bookService.createBook(request));

        assertEquals("Book with ISBN %s already exists".formatted(request.isbn()), exception.getMessage());
        verify(authorRepository).findById(authorId);
        verify(bookRepository).findByIsbn(request.isbn());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void shouldFindBooks() {
        var authorId = UUID.randomUUID();
        var bookId1 = UUID.randomUUID();
        var bookId2 = UUID.randomUUID();
        var pageable = PageRequest.of(0, 10);

        var author = Author.builder()
                .name("Test Author")
                .build();
        author.setId(authorId);

        var book1 = Book.builder()
                .title("Book 1")
                .author(author)
                .isbn("9780123456789")
                .publishedYear(2024)
                .build();
        book1.setId(bookId1);

        var book2 = Book.builder()
                .title("Book 2")
                .author(author)
                .isbn("9780123456790")
                .publishedYear(2023)
                .build();
        book2.setId(bookId2);

        var now = Instant.now();
        var bookResponse1 = new BookResponse(
                bookId1,
                "Book 1",
                authorId,
                author.getName(),
                "9780123456789",
                2024,
                true,
                now,
                now
        );

        var bookResponse2 = new BookResponse(
                bookId2,
                "Book 2",
                authorId,
                author.getName(),
                "9780123456790",
                2023,
                true,
                now,
                now
        );

        var books = List.of(book1, book2);
        var page = new PageImpl<>(books, pageable, 2);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(bookMapper.toResponse(book1)).thenReturn(bookResponse1);
        when(bookMapper.toResponse(book2)).thenReturn(bookResponse2);

        var result = bookService.findBooks(pageable);

        assertNotNull(result, "Result must not be null");
        assertEquals(2, result.getTotalElements(), "Total elements must be 2");
        assertEquals(2, result.getContent().size(), "Content size must be 2");
        assertEquals(bookId1, result.getContent().get(0).id(), "First book ID must match");
        assertEquals(bookId2, result.getContent().get(1).id(), "Second book ID must match");

        verify(bookRepository).findAll(pageable);
        verify(bookMapper, times(2)).toResponse(any(Book.class));
    }

    @Test
    void shouldReturnEmptyPageWhenNoBooksExist() {
        var pageable = PageRequest.of(0, 10);
        var emptyPage = new PageImpl<Book>(List.of(), pageable, 0);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        var result = bookService.findBooks(pageable);

        assertNotNull(result, "Result must not be null");
        assertTrue(result.isEmpty(), "Result must be empty");
        assertEquals(0, result.getTotalElements(), "Total elements must be 0");

        verify(bookRepository).findAll(pageable);
        verify(bookMapper, never()).toResponse(any(Book.class));
    }
}

