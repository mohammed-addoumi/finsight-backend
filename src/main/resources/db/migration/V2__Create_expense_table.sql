-- V1__create_transactions_table.sql
CREATE SEQUENCE expense_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE expense (
    id BIGINT PRIMARY KEY,
    date DATE NOT NULL,
    category VARCHAR(255) NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    description VARCHAR(255),
    payment_method VARCHAR(255) NOT NULL,
    merchant VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    username VARCHAR(255) NOT NULL
);
