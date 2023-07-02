--liquibase formatted sql logicalFilePath:databaseChangeLog.xml

--changeset alex:1
--comment: Initial structure
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE translation (
                            key TEXT,
                            locale TEXT,
                            value TEXT,
                            PRIMARY KEY (key, locale)
);

CREATE TABLE product_attribute (
        id UUID PRIMARY KEY,
        key TEXT,
        value TEXT,
        type TEXT
)
--rollback SELECT 1