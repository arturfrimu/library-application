package com.arturfrimu.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "authors", schema = "library")
@Audited
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = false)
public class Author extends BaseEntity {

    @Column(nullable = false, length = 150)
    String name;
}

