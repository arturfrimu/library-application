package com.arturfrimu.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "borrow_records", schema = "library")
@Audited
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = false)
public class BorrowRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    Book book;

    @Column(name = "borrow_date", nullable = false, updatable = false)
    Instant borrowDate;

    @Column(name = "return_date")
    Instant returnDate;
}

