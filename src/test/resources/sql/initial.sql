CREATE SCHEMA IF NOT EXISTS "portfolio";

CREATE TABLE IF NOT EXISTS "portfolio".portfolio
(
    id bigint NOT NULL,
    dateOfCreation date NOT NULL,
    name varchar(256) NOT NULL,
    description varchar(2048) NOT NULL,
    shortDescription varchar(1028) NOT NULL
    );

CREATE TABLE IF NOT EXISTS "portfolio".commodity_image
(
    id bigint NOT NULL,
    uri varchar NOT NULL,
    portfolio_id bigint NOT NULL,
    image_order INT NOT NULL
);
