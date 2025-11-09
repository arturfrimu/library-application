package com.arturfrimu.library.entity;

import com.arturfrimu.library.config.ClockProvider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "revinfo", schema = "library_history")
@RevisionEntity
public class RevInfo implements RevisionListener {

    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rev")
    Long revision;

    @RevisionTimestamp
    @Column(name = "revtmstmp")
    Long revisionTimestamp;

    @Override
    public void newRevision(Object revisionEntity) {
        log.debug("Creating new revision for entity: {}", revisionEntity.getClass().getSimpleName());
        RevInfo revInfo = (RevInfo) revisionEntity;
        revInfo.setRevisionTimestamp(ClockProvider.getCurrentTimeMillis());
        log.debug("Revision timestamp set to: {}", revInfo.getRevisionTimestamp());
    }
}