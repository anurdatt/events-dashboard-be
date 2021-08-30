INSERT INTO event(link, name, start, end) VALUES ('http://localhost:2332', 'test1', now(), now());
INSERT INTO event(link, name, start, end) VALUES ('http://localhost:6554', 'test2', now(), now());

INSERT INTO user(id, username, password, firstname, lastname) VALUES (1, 'test', '$2y$11$U6idvwDCG0HS/Brm5UxeJuAlzmvzAOi.y/421raVask9nFYMyE6dm', 'Test', 'User');
INSERT INTO user(id, username, password, firstname, lastname) VALUES (2, 'demo', '$2y$11$csgkbWpU0wZaTK9kONzBJOfbHOOfPK2sm3BJC0nnsjY01Jr7h5BkC', 'Demo', 'User');
