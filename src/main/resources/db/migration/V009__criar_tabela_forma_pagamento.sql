CREATE TABLE tb_formas_pagamento (
    codigo varchar(255) not null primary key,
    descricao varchar(50) not null unique
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;