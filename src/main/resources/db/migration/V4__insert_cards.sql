INSERT INTO card_type (type_name) VALUES
('CREDIT'),
('DEBIT')
ON CONFLICT DO NOTHING;

INSERT INTO card_variant
(variant_name, card_colour_front, card_colour_back, chip_image, text_colour)
VALUES
('CLASSIC', '#1D3557', '#0A1A2F', 'GoldChip.png', '#FFFFFF'),
('GOLD', '#C9A227', '#A7893D', 'SilverChip.png', '#000000'),
('PLATINUM', '#2E2E2E', '#1A1A1A', 'ShinyChip.png', '#FFFFFF')
ON CONFLICT DO NOTHING;

INSERT INTO card_network (network_name) VALUES
('MASTERCARD'),
('VISA'),
('RUPAY')
ON CONFLICT DO NOTHING;

INSERT INTO network_bin (network_id, bin_number) VALUES
    (1, '51'),
    (2, '40'),
    (3, '60')
ON CONFLICT DO NOTHING;

INSERT INTO reason (reason_name)
VALUES
('NEW CARD'),
('LOST CARD'),
('REPLACEMENT'),
('UPGRADE'),
('DAMAGED CARD')
ON CONFLICT DO NOTHING;