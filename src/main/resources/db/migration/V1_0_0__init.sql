CREATE TABLE categories(
    id   bigserial PRIMARY KEY,
    name text
);

CREATE TABLE products(
    id          bigserial PRIMARY KEY,
    title       text,
    price       int,
    category_id int,
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

INSERT INTO categories(name)
VALUES
    ('Компьютеры'),
    ('Бытовая техника'),
    ('Электроника'),
    ('Прочее');

INSERT INTO products(title, price, category_id)
VALUES
    ('Пылесос', 100, (SELECT id FROM categories WHERE name='Бытовая техника')),
    ('Ноутбук', 200, (SELECT id FROM categories WHERE name='Компьютеры'));
