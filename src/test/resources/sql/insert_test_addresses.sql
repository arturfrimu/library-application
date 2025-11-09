INSERT INTO library.addresses (id, country_id, address, zip_code, city, active)
VALUES ('66666666-6666-6666-6666-666666666666', '11111111-1111-1111-1111-111111111111', 'Test Street 1', '12345', 'Test City', true),
       ('77777777-7777-7777-7777-777777777777', '11111111-1111-1111-1111-111111111111', 'Test Street 2', '12346', 'Test City', true)
ON CONFLICT (id) DO NOTHING;