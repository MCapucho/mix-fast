CREATE TABLE tb_clientes (
    codigo varchar(255) not null primary key,
    nome varchar(100) not null,
    cpf varchar(11) not null unique,
    email varchar(200) not null unique
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;