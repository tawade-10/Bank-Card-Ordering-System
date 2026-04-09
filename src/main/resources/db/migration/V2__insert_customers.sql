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
    '$2a$10$3p7mPUuWZqtD9c1hPaqh7O6eqoRVax0tL3FV2nFatE2BqdJ1TTn5W',
    'ADMIN',
    CURRENT_DATE,
    CURRENT_TIME
)
ON CONFLICT (email) DO NOTHING;