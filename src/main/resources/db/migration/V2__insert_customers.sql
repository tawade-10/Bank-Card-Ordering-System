INSERT INTO customers (
    name,
    email,
    password,
    roles,
    created_date,
    created_time
) VALUES (
    'Admin User',
    'admin@bank.com',
    'admin123',
    'ADMIN',
    CURRENT_DATE,
    CURRENT_TIME
)
ON CONFLICT (email) DO NOTHING;