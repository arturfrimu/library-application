package com.arturfrimu.library.controller;

import com.arturfrimu.library.dto.request.CreateBookRequest;
import com.arturfrimu.library.dto.response.BookResponse;
import com.arturfrimu.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookController {

    BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request) {
        log.info("Received request to create book: {}", request.title());
        var response = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> findBooks(Pageable pageable) {
        log.info("Received request to find books with pagination");
        var response = bookService.findBooks(pageable);
        return ResponseEntity.ok(response);
    }
}

