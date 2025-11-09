package com.arturfrimu.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "books", schema = "library")
@Audited
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = false)
public class Book extends BaseEntity {

    @Column(nullable = false, length = 200)
    String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    Author author;

    @Column(nullable = false, length = 20, unique = true)
    String isbn;

    @Column(name = "published_year", nullable = false)
    Integer publishedYear;
}

