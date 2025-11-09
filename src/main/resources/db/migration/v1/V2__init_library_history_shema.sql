CREATE TABLE library_history.revinfo
(
    rev       BIGSERIAL PRIMARY KEY,
    revtmstmp BIGINT
);

CREATE TABLE library_history.countries_history
(
    id            UUID         NOT NULL,
    revision      BIGINT       NOT NULL,
    revision_type SMALLINT     NOT NULL,
    active        BOOLEAN      NOT NULL DEFAULT TRUE,
    name          VARCHAR(128) NOT NULL,
    code          VARCHAR(3)   NOT NULL,
    created       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),

    CONSTRAINT pk_counties_history PRIMARY KEY (id, revision),
    CONSTRAINT fk_countries_history_rev FOREIGN KEY (revision) REFERENCES library_history.revinfo (rev)
);

CREATE INDEX IF NOT EXISTS idx_countries_history_revision ON library_history.countries_history (revision);

CREATE TABLE library_history.addresses_history
(
    id            UUID         NOT NULL,
    revision      BIGINT       NOT NULL,
    revision_type SMALLINT     NOT NULL,
    active        BOOLEAN      NOT NULL DEFAULT TRUE,
    country_id    UUID         NOT NULL,
    address       VARCHAR(128) NOT NULL,
    zip_code      VARCHAR(32)  NOT NULL,
    city          VARCHAR(128) NOT NULL,
    created       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),

    CONSTRAINT pk_addresses_history PRIMARY KEY (id, revision),
    CONSTRAINT fk_addresses_history_rev FOREIGN KEY (revision) REFERENCES library_history.revinfo (rev)
);

CREATE INDEX IF NOT EXISTS idx_addresses_history_revision ON library_history.addresses_history (revision);


CREATE TABLE library_history.users_history
(
    id            UUID          NOT NULL,
    revision      BIGINT        NOT NULL,
    revision_type SMALLINT      NOT NULL,
    active        BOOLEAN       NOT NULL DEFAULT TRUE,
    email         VARCHAR(1024) NOT NULL,
    first_name    VARCHAR(64)   NOT NULL,
    last_name     VARCHAR(64)   NOT NULL,
    address_id    UUID          NOT NULL,
    created       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),

    CONSTRAINT pk_users_history PRIMARY KEY (id, revision),
    CONSTRAINT fk_users_history_rev FOREIGN KEY (revision) REFERENCES library_history.revinfo (rev)
);

CREATE INDEX IF NOT EXISTS idx_users_history_revision ON library_history.users_history (revision);

CREATE TABLE library_history.authors_history
(
    id            UUID         NOT NULL,
    revision      BIGINT       NOT NULL,
    revision_type SMALLINT     NOT NULL,
    active        BOOLEAN      NOT NULL DEFAULT TRUE,
    name          VARCHAR(150) NOT NULL,
    created       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),

    CONSTRAINT pk_authors_history PRIMARY KEY (id, revision),
    CONSTRAINT fk_authors_history_rev FOREIGN KEY (revision) REFERENCES library_history.revinfo (rev)
);

CREATE INDEX IF NOT EXISTS idx_authors_history_revision ON library_history.authors_history (revision);

CREATE TABLE library_history.books_history
(
    id             UUID         NOT NULL,
    revision       BIGINT       NOT NULL,
    revision_type  SMALLINT     NOT NULL,
    active         BOOLEAN      NOT NULL DEFAULT TRUE,
    title          VARCHAR(200) NOT NULL,
    author_id      UUID         NOT NULL,
    isbn           VARCHAR(20)  NOT NULL,
    published_year INT          NOT NULL,
    created        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),

    CONSTRAINT pk_books_history PRIMARY KEY (id, revision),
    CONSTRAINT fk_books_history_rev FOREIGN KEY (revision) REFERENCES library_history.revinfo (rev)
);

CREATE INDEX IF NOT EXISTS idx_books_history_revision ON library_history.books_history (revision);

CREATE TABLE library_history.borrow_records_history
(
    id            UUID     NOT NULL,
    revision      BIGINT   NOT NULL,
    revision_type SMALLINT NOT NULL,
    user_id       UUID     NOT NULL,
    book_id       UUID     NOT NULL,
    borrow_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    return_date   TIMESTAMP WITHOUT TIME ZONE,
    active        BOOLEAN  NOT NULL DEFAULT TRUE,
    created       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),

    CONSTRAINT pk_borrow_records_history PRIMARY KEY (id, revision),
    CONSTRAINT fk_borrow_records_history_rev FOREIGN KEY (revision) REFERENCES library_history.revinfo (rev)
);

CREATE INDEX IF NOT EXISTS idx_borrow_records_history_revision ON library_history.borrow_records_history (revision);