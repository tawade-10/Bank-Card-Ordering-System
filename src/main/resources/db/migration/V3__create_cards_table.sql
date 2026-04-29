CREATE TABLE card_type (
    type_id BIGSERIAL PRIMARY KEY,
    type_name VARCHAR(255) NOT NULL
);

CREATE TABLE card_variant (
    variant_id BIGSERIAL PRIMARY KEY,
    variant_name VARCHAR(255) NOT NULL,
    card_colour_front VARCHAR(100),
    card_colour_back VARCHAR(100),
    chip_colour VARCHAR(50),
    text_colour VARCHAR(50)
);

CREATE TABLE card_network (
    network_id BIGSERIAL PRIMARY KEY,
    network_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE network_bin (
    bin_id BIGSERIAL PRIMARY KEY,
    network_id BIGINT NOT NULL,
    bin_number VARCHAR(10) NOT NULL,

    CONSTRAINT fk_network_bin_network
        FOREIGN KEY (network_id)
        REFERENCES card_network(network_id)
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
    network_id BIGINT,
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

    CONSTRAINT fk_request_network
        FOREIGN KEY (network_id)
        REFERENCES card_network(network_id),

    CONSTRAINT fk_request_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
);