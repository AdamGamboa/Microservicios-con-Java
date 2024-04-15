create table if not exists orders
(
    id bigint NOT NULL AUTO_INCREMENT,
    user_id bigint not null,
    event_id bigint not null,
    cantidad_tiquetes int,
    estado varchar(32),

    PRIMARY KEY (`id`)
);