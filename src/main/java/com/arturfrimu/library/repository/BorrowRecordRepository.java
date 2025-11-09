package com.arturfrimu.library.repository;

import com.arturfrimu.library.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, UUID> {
    @Query("""
            SELECT br FROM BorrowRecord br
            WHERE br.user.id = :userId
            AND br.returnDate IS NULL
            AND br.active = true
            """)
    List<BorrowRecord> findActiveBorrowsByUserId(@Param("userId") UUID userId);

    @Query("""
            SELECT br FROM BorrowRecord br
            WHERE br.book.id = :bookId
            AND br.returnDate IS NULL
            AND br.active = true
            """)
    List<BorrowRecord> findActiveBorrowsByBookId(@Param("bookId") UUID bookId);
}

