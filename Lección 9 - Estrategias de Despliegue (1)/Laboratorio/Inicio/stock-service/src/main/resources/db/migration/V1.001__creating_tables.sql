create table if not exists stocks
(
    id bigint NOT NULL AUTO_INCREMENT,
    stock_quantity bigint,
    PRIMARY KEY (`id`)
);
