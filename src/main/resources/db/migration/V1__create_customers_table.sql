CREATE TABLE customers (
    customer_id     BIGSERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    roles           VARCHAR(50) NOT NULL,
    created_date    DATE,
    created_time    TIME
);
