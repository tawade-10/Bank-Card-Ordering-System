INSERT INTO bank (bank_name, bank_code)
VALUES
('State Bank of India', 'SBI'),
('HDFC Bank', 'HDFC'),
('ICICI Bank', 'ICICI'),
('Axis Bank', 'AXIS'),
('Punjab National Bank', 'PNB'),
('Bank of Baroda', 'BOB'),
('Kotak Mahindra Bank', 'KOTAK'),
('IndusInd Bank', 'INDUSIND'),
('Canara Bank', 'CANARA'),
('Union Bank of India', 'UBI')
ON CONFLICT DO NOTHING;