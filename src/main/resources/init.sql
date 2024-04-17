create table if not exists product
(
    id           uuid primary key,
    name         varchar not null,
    product_type varchar not null,
    cost         int     not null
);

create table if not exists customer
(
    id    uuid primary key,
    name  varchar not null,
    phone varchar not null,
    age   int     not null
);

create table if not exists "order"
(
    id          uuid primary key,
    customer_id uuid not null references customer (id),
    product_id  uuid not null references product (id),
    count       int  not null,
    amount      int  not null
);