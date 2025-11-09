package com.arturfrimu.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "users", schema = "library")
@Audited
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = false)
public class User extends BaseEntity {

    @Column(nullable = false, length = 1024)
    String email;

    @Column(name = "first_name", nullable = false, length = 64)
    String firstName;

    @Column(name = "last_name", nullable = false, length = 64)
    String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    Address address;
}

