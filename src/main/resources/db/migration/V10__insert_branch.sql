INSERT INTO branch
(bank_id, branch_name, city, ifsc_code)
VALUES

(1, 'Mumbai Main Branch', 'Mumbai', 'SBIN0001001'),
(1, 'Pune Branch', 'Pune', 'SBIN0001002'),

(2, 'Andheri Branch', 'Mumbai', 'HDFC0001001'),
(2, 'Thane Branch', 'Thane', 'HDFC0001002'),

(3, 'Bandra Branch', 'Mumbai', 'ICIC0001001'),
(3, 'Navi Mumbai Branch', 'Navi Mumbai', 'ICIC0001002'),

(4, 'Dadar Branch', 'Mumbai', 'UTIB0001001'),
(4, 'Pune Camp Branch', 'Pune', 'UTIB0001002'),

(5, 'Mumbai Branch', 'Mumbai', 'PUNB0001001'),
(5, 'Nagpur Branch', 'Nagpur', 'PUNB0001002')

ON CONFLICT DO NOTHING;