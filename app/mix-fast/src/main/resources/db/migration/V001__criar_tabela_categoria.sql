CREATE TABLE tb_categorias (
    codigo varchar(255) not null primary key,
    nome varchar(50) not null unique
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;