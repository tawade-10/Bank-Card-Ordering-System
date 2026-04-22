INSERT INTO card_type (type_name) VALUES
('DEBIT'),
('CREDIT')
ON CONFLICT DO NOTHING;

INSERT INTO card_variant
(variant_name, card_colour_front, card_colour_back, chip_colour, text_colour)
VALUES
('CLASSIC',
 '#0A2342',
 '#1D3557',
 '#D4AF37',
 '#FFFFFF'
),
('GOLD',
 '#C9A227',
 '#F2D16B',
 '#E5C158',
 '#3A2E04'
),

('PLATINUM',
 '#8E9BAE',
 '#B8C4D4',
 '#E0E3E7',
 '#1C1C1C'
)
ON CONFLICT DO NOTHING;

INSERT INTO reason (reason_name) VALUES
('NEW CARD'),
('LOST CARD'),
('REPLACEMENT'),
('UPGRADE'),
('DAMAGED CARD')
ON CONFLICT DO NOTHING;