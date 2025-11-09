package com.arturfrimu.library.service;

import com.arturfrimu.library.dto.request.BorrowBookRequest;
import com.arturfrimu.library.dto.request.ReturnBookRequest;
import com.arturfrimu.library.dto.response.BorrowedBookResponse;
import com.arturfrimu.library.entity.BorrowRecord;
import com.arturfrimu.library.exception.ResourceAlreadyExists;
import com.arturfrimu.library.exception.ResourceNotFound;
import com.arturfrimu.library.mapper.BorrowedBookMapper;
import com.arturfrimu.library.repository.BookRepository;
import com.arturfrimu.library.repository.BorrowRecordRepository;
import com.arturfrimu.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BorrowService {

    BorrowedBookMapper borrowedBookMapper;

    Clock clock;
    BookRepository bookRepository;
    UserRepository userRepository;
    BorrowRecordRepository borrowRecordRepository;

    @Transactional
    public UUID borrowBook(BorrowBookRequest request) {
        log.info("Borrowing book {} for user {}", request.bookId(), request.userId());

        var user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFound("User not found with id: %s".formatted(request.userId())));

        var book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new ResourceNotFound("Book not found with id: %s".formatted(request.bookId())));

        var activeBorrows = borrowRecordRepository.findActiveBorrowsByBookId(request.bookId());
        if (!activeBorrows.isEmpty()) {
            throw new ResourceAlreadyExists("Book with id %s is already borrowed".formatted(request.bookId()));
        }

        var borrowRecord = BorrowRecord.builder()
                .user(user)
                .book(book)
                .borrowDate(clock.instant())
                .returnDate(null)
                .build();

        var savedRecord = borrowRecordRepository.save(borrowRecord);
        log.info("Book {} borrowed successfully by user {}", request.bookId(), request.userId());

        return savedRecord.getId();
    }

    @Transactional
    public UUID returnBook(ReturnBookRequest request) {
        log.info("Returning book with borrow record id: {}", request.borrowRecordId());

        var borrowRecord = borrowRecordRepository.findById(request.borrowRecordId())
                .orElseThrow(() -> new ResourceNotFound("Borrow record not found with id: %s".formatted(request.borrowRecordId())));

        if (borrowRecord.getReturnDate() != null) {
            throw new ResourceAlreadyExists("Book with borrow record id %s is already returned".formatted(request.borrowRecordId()));
        }

        borrowRecord.setReturnDate(clock.instant());
        var borrowedRecord = borrowRecordRepository.save(borrowRecord);

        log.info("Book returned successfully with borrow record id: {}", request.borrowRecordId());

        return borrowedRecord.getId();
    }

    @Transactional(readOnly = true)
    public List<BorrowedBookResponse> getBorrowedBooksByUserId(UUID userId) {
        log.info("Fetching borrowed books for user id: {}", userId);

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFound("User not found with id: %s".formatted(userId));
        }

        var borrowRecords = borrowRecordRepository.findActiveBorrowsByUserId(userId);

        return borrowRecords.stream()
                .map(borrowedBookMapper::toResponse)
                .toList();
    }
}

