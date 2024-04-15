create table if not exists events
(
    id bigint NOT NULL AUTO_INCREMENT,
    nombre varchar(256) not null,
    total_espacios int,
    espacios_adquiridos int,
    fecha_evento timestamp,
    fecha_venta_boletos timestamp,
    ubicacion varchar(512) not null,

    PRIMARY KEY (`id`)
);
