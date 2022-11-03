create table author_seq
(
    next_val bigint
) engine = InnoDB;
insert into author_seq
values (1);
create table authors
(
    id         bigint not null,
    first_name varchar(255),
    full_name  varchar(255),
    last_name  varchar(255),
    primary key (id)
) engine = InnoDB;
create table book_seq
(
    next_val bigint
) engine = InnoDB;
insert into book_seq
values (1);
create table books
(
    id          bigint not null,
    description varchar(2048),
    photo_path  varchar(255),
    price       decimal(19, 2),
    title       varchar(256),
    primary key (id)
) engine = InnoDB;
create table books_author
(
    author_id bigint,
    book_id   bigint not null,
    primary key (book_id)
) engine = InnoDB;
create table books_categories
(
    book_id     bigint not null,
    category_id bigint not null
) engine = InnoDB;
create table books_genres
(
    book_id  bigint not null,
    genre_id bigint not null
) engine = InnoDB;
create table bucket_seq
(
    next_val bigint
) engine = InnoDB;
insert into bucket_seq
values (1);
create table buckets
(
    id      bigint not null,
    user_id bigint,
    primary key (id)
) engine = InnoDB;
create table buckets_books
(
    bucket_id bigint not null,
    book_id   bigint not null
) engine = InnoDB;
create table categories
(
    id    bigint not null,
    title varchar(255),
    primary key (id)
) engine = InnoDB;
create table category_seq
(
    next_val bigint
) engine = InnoDB;
insert into category_seq
values (1);
create table genre_seq
(
    next_val bigint
) engine = InnoDB;
insert into genre_seq
values (1);
create table genres
(
    id    bigint not null,
    title varchar(255),
    primary key (id)
) engine = InnoDB;
create table receipt_details_seq
(
    next_val bigint
) engine = InnoDB;
insert into receipt_details_seq
values (1);
create table receipt_seq
(
    next_val bigint
) engine = InnoDB;
insert into receipt_seq
values (1);
create table receipts
(
    id          bigint not null,
    address     varchar(255),
    created     datetime(6),
    description varchar(255),
    status      varchar(255),
    sum         decimal(19, 2),
    updated     datetime(6),
    user_id     bigint,
    primary key (id)
) engine = InnoDB;
create table receipts_details
(
    id         bigint not null,
    amount     decimal(19, 2),
    price      decimal(19, 2),
    book_id    bigint,
    receipt_id bigint,
    details_id bigint not null,
    primary key (id)
) engine = InnoDB;
create table user_seq
(
    next_val bigint
) engine = InnoDB;
insert into user_seq
values (1);
create table users
(
    id              bigint not null,
    activation_code varchar(255),
    active          bit    not null,
    email           varchar(255),
    password        varchar(255),
    role            varchar(255),
    username        varchar(255),
#     bucket_id       bigint,
    primary key (id)
) engine = InnoDB;
alter table receipts_details
    add constraint UK_55fvqj3auve02cbxx3li2flji unique (details_id);
alter table books_author
    add constraint FKwf964bsm721a4j7yl3bh88ii foreign key (author_id) references authors (id);
alter table books_author
    add constraint FKsueqniexbdu6fie7t3evkygv foreign key (book_id) references books (id);
alter table books_categories
    add constraint FK4klp9o273ej1ywgmie14rvdx3 foreign key (category_id) references categories (id);
alter table books_categories
    add constraint FKmsuoucvyyccli3f6u59co41cq foreign key (book_id) references books (id);
alter table books_genres
    add constraint FKgkat05y2cec3tcpl6ur250sd0 foreign key (genre_id) references genres (id);
alter table books_genres
    add constraint FKlv42b6uemg63q27om39jjbt9o foreign key (book_id) references books (id);
alter table buckets
    add constraint FKnl0ltaj67xhydcrfbq8401nvj foreign key (user_id) references users (id);
alter table buckets_books
    add constraint FK84mas7y1bishap4hca7sp4mg6 foreign key (book_id) references books (id);
alter table buckets_books
    add constraint FKgwqwnyv4iilbyq548g2xuotpd foreign key (bucket_id) references buckets (id);
alter table receipts
    add constraint FK7t0uo7yxjck29e967rny84ky4 foreign key (user_id) references users (id);
alter table receipts_details
    add constraint FKdw2o48o9yh1e54qto97ndhg81 foreign key (book_id) references books (id);
alter table receipts_details
    add constraint FK6hm1xopolublv6et1tudxm9qw foreign key (receipt_id) references receipts (id);
alter table receipts_details
    add constraint FKhstxhmcwgtvpuhas0ktn859oe foreign key (details_id) references receipts_details (id);
# alter table users
#     add constraint FK8l2qc4c6gihjdyoch727guci foreign key (bucket_id) references buckets (id);