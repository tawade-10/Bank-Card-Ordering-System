CREATE TABLE card_type (
    type_id BIGSERIAL PRIMARY KEY,
    type_name VARCHAR(255) NOT NULL
);

CREATE TABLE card_variant (
    variant_id BIGSERIAL PRIMARY KEY,
    variant_name VARCHAR(255) NOT NULL
);

CREATE TABLE reason (
    reason_id BIGSERIAL PRIMARY KEY,
    reason_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE request_card (
    request_id BIGSERIAL PRIMARY KEY,
    card_type_id BIGINT NOT NULL,
    card_variant_id BIGINT NOT NULL,
    reason_id BIGINT NOT NULL,
    status VARCHAR(50),
    date DATE,
    customer_id BIGINT,

    CONSTRAINT fk_request_card_type
        FOREIGN KEY (card_type_id)
        REFERENCES card_type(type_id),

    CONSTRAINT fk_request_card_variant
        FOREIGN KEY (card_variant_id)
        REFERENCES card_variant(variant_id),

    CONSTRAINT fk_request_reason
        FOREIGN KEY (reason_id)
        REFERENCES reason(reason_id),

    CONSTRAINT fk_request_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
);