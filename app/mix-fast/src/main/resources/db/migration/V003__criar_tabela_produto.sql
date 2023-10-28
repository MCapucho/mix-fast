CREATE TABLE tb_produtos (
    codigo varchar(255) not null primary key,
    nome varchar(100) not null unique,
    descricao varchar(200),
    preco decimal(10, 2),
    categoria_codigo varchar(255) not null
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;