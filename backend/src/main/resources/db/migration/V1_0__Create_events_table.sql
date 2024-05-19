CREATE TABLE events
(
    id          UUID                        NOT NULL,
    title       VARCHAR(255)                NOT NULL,
    description VARCHAR(255)                NOT NULL,
    date        date                        NOT NULL,
    time        time WITHOUT TIME ZONE      NOT NULL,
    duration    BIGINT                      NOT NULL,
    location    VARCHAR(255)                NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_events PRIMARY KEY (id)
);
