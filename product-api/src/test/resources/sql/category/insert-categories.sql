INSERT INTO categories (id, name, parent_id)
VALUES
    (1, 'Tech devices and gadgets', null),
    (2, 'Smartphones and tablets', 1),
    (3, 'Games and accessories', 1),
    (4, 'Smartphones with Android OS', 2),
    (5, 'Apple iPhone', 2);