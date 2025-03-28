--liquibase formatted sql

--changeset juhan:sanctioned-name

CREATE TABLE sanctioned_name
(
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    full_name       VARCHAR NOT NULL,
    normalized_name VARCHAR NOT NULL,
    phonetic_key    VARCHAR  NOT NULL,
    created_at      TIMESTAMP NOT NULL
);

comment on column sanctioned_name.full_name is 'Original sanctioned name';
comment on column sanctioned_name.normalized_name is 'Preprocessed name (lowercase, no noise words)';
comment on column sanctioned_name.phonetic_key is 'Phonetic representation (Soundex/Double Metaphone)';
