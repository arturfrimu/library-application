INSERT INTO library.countries (id, name, code)
VALUES ('11111111-1111-1111-1111-111111111111', 'Test Country', 'TST')
ON CONFLICT (id) DO NOTHING;

