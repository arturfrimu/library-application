package com.arturfrimu.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "countries", schema = "library")
@Audited
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = false)
public class Country extends BaseEntity {

    @Column(nullable = false, length = 128)
    String name;

    @Column(nullable = false, length = 3)
    String code;
}

