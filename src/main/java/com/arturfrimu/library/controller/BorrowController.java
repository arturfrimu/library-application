package com.arturfrimu.library.controller;

import com.arturfrimu.library.dto.request.BorrowBookRequest;
import com.arturfrimu.library.dto.request.ReturnBookRequest;
import com.arturfrimu.library.dto.response.BorrowedBookResponse;
import com.arturfrimu.library.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BorrowController {

    BorrowService borrowService;

    @PostMapping
    public ResponseEntity<UUID> borrowBook(@Valid @RequestBody BorrowBookRequest request) {
        log.info("Received request to borrow book {} for user {}", request.bookId(), request.userId());
        var borrowedRecord = borrowService.borrowBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowedRecord);
    }

    @PostMapping("/return")
    public ResponseEntity<UUID> returnBook(@Valid @RequestBody ReturnBookRequest request) {
        log.info("Received request to return book with borrow record id: {}", request.borrowRecordId());
        var borrowedRecord = borrowService.returnBook(request);
        return ResponseEntity.ok().body(borrowedRecord);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<BorrowedBookResponse>> getBorrowedBooks(@PathVariable UUID userId) {
        log.info("Received request to get borrowed books for user id: {}", userId);
        var response = borrowService.getBorrowedBooksByUserId(userId);
        return ResponseEntity.ok(response);
    }
}

