CREATE TABLE library.countries
(
    id      UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    active  boolean      NOT NULL DEFAULT TRUE,
    name    VARCHAR(128) NOT NULL,
    code    VARCHAR(3)   NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE   NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated TIMESTAMP WITHOUT TIME ZONE   NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    version INT                   default 0
);

CREATE TABLE library.addresses
(
    id         UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    active     boolean      NOT NULL DEFAULT TRUE,
    country_id UUID         NOT NULL REFERENCES library.countries (id),
    address    VARCHAR(128) NOT NULL,
    zip_code   VARCHAR(32)  NOT NULL,
    city       VARCHAR(64)  NOT NULL,
    created    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    version    INT                   default 0
);

CREATE TABLE library.users
(
    id         UUID PRIMARY KEY       DEFAULT uuid_generate_v4(),
    active     boolean       NOT NULL DEFAULT TRUE,
    email      VARCHAR(1024) NOT NULL,
    first_name VARCHAR(64)   NOT NULL,
    last_name  VARCHAR(64)   NOT NULL,
    address_id UUID          NOT NULL REFERENCES library.addresses (id),
    created    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    version    INT                    default 0
);

CREATE INDEX IF NOT EXISTS idx_users_email ON library.users (email);

CREATE TABLE library.authors
(
    id      UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    name    VARCHAR(150) NOT NULL,
    active  BOOLEAN      NOT NULL DEFAULT TRUE,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    version INT                   default 0
);

CREATE TABLE library.books
(
    id             UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    title          VARCHAR(200) NOT NULL,
    active         BOOLEAN      NOT NULL DEFAULT TRUE,
    author_id      UUID         NOT NULL REFERENCES library.authors (id) ON DELETE CASCADE,
    isbn           VARCHAR(20)  NOT NULL UNIQUE,
    published_year INT          NOT NULL,
    created        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    version        INT                   default 0
);

CREATE TABLE library.borrow_records
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id     UUID    NOT NULL REFERENCES library.users (id) ON DELETE CASCADE,
    book_id     UUID    NOT NULL REFERENCES library.books (id) ON DELETE CASCADE,
    borrow_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    return_date TIMESTAMP WITHOUT TIME ZONE,
    active      BOOLEAN NOT NULL DEFAULT TRUE,
    created     TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    updated     TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    version     INT              default 0
);

CREATE INDEX IF NOT EXISTS idx_borrow_records_user_id ON library.borrow_records (user_id);
CREATE INDEX IF NOT EXISTS idx_borrow_records_book_id ON library.borrow_records (book_id);
CREATE INDEX IF NOT EXISTS idx_borrow_records_borrow_date ON library.borrow_records (borrow_date);
CREATE INDEX IF NOT EXISTS idx_borrow_records_return_date ON library.borrow_records (return_date);