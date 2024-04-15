create table if not exists users
(
    id bigint NOT NULL AUTO_INCREMENT,
    nombre varchar(256) not null,
    direccion varchar(512),
    email varchar(128),
    estado varchar(32),
    tiquetes_comprados int,

    PRIMARY KEY (`id`)
);
