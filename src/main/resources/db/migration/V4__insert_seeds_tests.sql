

INSERT INTO tb_user (name, email, password, role)
VALUES (
           'admin',
           'admin@gmail.com',
           '$2a$10$pnqmOJBD7neoEOF8XZcxa.H0IHbae5JsKI7n.BDFJa11qyQOXr4qm',
           'ADMIN'
       );

INSERT INTO tb_restaurant_table (name, capacity, status) VALUES
                                                             ('Mesa 1', 2, 'AVAILABLE'),
                                                             ('Mesa 2', 4, 'AVAILABLE'),
                                                             ('Mesa 3', 2, 'AVAILABLE'),
                                                             ('Mesa 4', 6, 'AVAILABLE'),
                                                             ('Mesa 5', 4, 'AVAILABLE'),
                                                             ('Mesa 6', 2, 'AVAILABLE'),
                                                             ('Mesa 7', 4, 'AVAILABLE'),
                                                             ('Mesa 8', 6, 'AVAILABLE'),
                                                             ('Mesa 9', 2, 'AVAILABLE'),
                                                             ('Mesa 10', 4, 'AVAILABLE');
