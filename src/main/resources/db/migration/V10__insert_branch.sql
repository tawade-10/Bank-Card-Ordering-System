INSERT INTO branch
(
    bank_id,
    branch_name,
    city,
    ifsc_code,
    branch_code
)
VALUES
(1,'Mumbai Main Branch','Mumbai','SBIN0001001','MUM001'),
(1,'Pune Branch','Pune','SBIN0001002','PUN001'),
(2,'Andheri Branch','Mumbai','HDFC0001001','AND001'),
(2,'Thane Branch','Thane','HDFC0001002','THA001'),
(3,'Bandra Branch','Mumbai','ICIC0001001','BAN001'),
(3,'Navi Mumbai Branch','Navi Mumbai','ICIC0001002','NAV001'),
(4,'Dadar Branch','Mumbai','UTIB0001001','DAD001'),
(4,'Pune Camp Branch','Pune','UTIB0001002','PCM001'),
(5,'Mumbai Branch','Mumbai','PUNB0001001','PMUM001'),
(5,'Nagpur Branch','Nagpur','PUNB0001002','PNAG001')

ON CONFLICT DO NOTHING;