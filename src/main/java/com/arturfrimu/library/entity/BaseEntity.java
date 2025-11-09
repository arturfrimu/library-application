package com.arturfrimu.library.entity;

import com.arturfrimu.library.config.ClockProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    UUID id;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "active", nullable = false)
    Boolean active;

    @NotNull
    @ColumnDefault("(now() AT TIME ZONE 'utc')")
    @Column(name = "created", nullable = false, updatable = false)
    Instant created;

    @NotNull
    @ColumnDefault("(now() AT TIME ZONE 'utc')")
    @Column(name = "updated", nullable = false)
    Instant updated;

    @Version
    @ColumnDefault("0")
    @Column(name = "version", nullable = false)
    Integer version;

    @PrePersist
    protected void onCreate() {
        if (active == null) {
            active = true;
        }
        if (created == null) {
            created = ClockProvider.getCurrentInstant();
        }
        if (updated == null) {
            updated = ClockProvider.getCurrentInstant();
        }
        if (version == null) {
            version = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updated = ClockProvider.getCurrentInstant();
    }
}