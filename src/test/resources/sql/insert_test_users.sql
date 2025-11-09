INSERT INTO library.users (id, email, first_name, last_name, address_id, active)
VALUES ('88888888-8888-8888-8888-888888888888', 'test.user1@example.com', 'Test', 'User1', '66666666-6666-6666-6666-666666666666', true),
       ('99999999-9999-9999-9999-999999999999', 'test.user2@example.com', 'Test', 'User2', '77777777-7777-7777-7777-777777777777', true),
       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'test.user3@example.com', 'Test', 'User3', '66666666-6666-6666-6666-666666666666', true)
ON CONFLICT (id) DO NOTHING;