create table categories (
    id              bigserial primary key,
    title           varchar(255) unique,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into categories (title) values ('Food'), ('Others');

create table products
(
    id              bigserial primary key,
    title           varchar(255),
    category_id     bigint references categories (id),
    price           decimal (10,2),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into products (title, price, category_id) values
('Milk', 80, 1), ('Bread', 25, 1), ('Cheese', 300, 1),('Ddasd', 2125, 1),('dqd', 2125, 1),('Brevscvd', 25312, 1),(' fad', 2532, 1),('dqwd', 225, 1);

create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price decimal (10,2) not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price_per_product decimal (10,2)    not null,
    price             decimal (10,2)   not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);


