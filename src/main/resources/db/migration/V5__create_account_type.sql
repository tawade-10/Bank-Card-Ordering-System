CREATE TABLE account_type (
    type_id BIGSERIAL PRIMARY KEY,
    type_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE account (
    account_id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    account_number VARCHAR(30) UNIQUE,
    type_id BIGINT NOT NULL,
    balance DECIMAL(15,2) DEFAULT 0.00,
    status VARCHAR(50),
    created_date DATE,
    created_time TIME,

    CONSTRAINT fk_account_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id),

    CONSTRAINT fk_account_type
        FOREIGN KEY (type_id)
        REFERENCES account_type(type_id)
);

