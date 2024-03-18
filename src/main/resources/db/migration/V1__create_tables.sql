create table payments (
    payment_id bigserial not null,
    balance numeric(38,2),
    payment_status varchar(255),
    seller_id bigint,
    primary key (payment_id));

create table sellers (
    id bigserial not null,
    seller_name varchar(255),
    primary key (id));

alter table if exists payments add constraint FKdplcwgo3ik7lobk2ro9w17bg7 foreign key (seller_id) references sellers;