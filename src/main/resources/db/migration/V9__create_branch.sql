CREATE TABLE branch (
    branch_id BIGSERIAL PRIMARY KEY,
    bank_id BIGINT NOT NULL,
    branch_name VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    ifsc_code VARCHAR(20) NOT NULL UNIQUE,
    branch_code VARCHAR(20) UNIQUE,
    next_account_sequence BIGINT NOT NULL DEFAULT 1,
    CONSTRAINT fk_branch_bank
        FOREIGN KEY (bank_id)
        REFERENCES bank(bank_id)
);