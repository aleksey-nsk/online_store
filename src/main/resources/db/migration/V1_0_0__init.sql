create table products(
    id      bigserial   primary key,
    title   text,
    price   int
);

insert into products(title, price)
values
    ('Тестовый товар А', 100),
    ('Тестовый товар Б', 200);
