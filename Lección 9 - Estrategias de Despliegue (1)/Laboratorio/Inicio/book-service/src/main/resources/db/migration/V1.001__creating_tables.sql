create table if not exists books
(
    id bigint NOT NULL AUTO_INCREMENT,
    title varchar(256) not null,
    summary varchar(512),
    author varchar(128),
    published_year int,
    stock_id bigint,

    PRIMARY KEY (`id`)
);
