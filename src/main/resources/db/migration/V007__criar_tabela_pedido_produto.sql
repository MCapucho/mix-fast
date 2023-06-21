CREATE TABLE tb_pedidos_produtos (
    codigo varchar(255) not null primary key,
    quantidade int not null,
    observacao varchar(150),
    preco_unitario decimal(10, 2) not null,
    preco_total decimal(10, 2) not null,
    pedido_codigo varchar(255) not null,
    produto_codigo varchar(255) not null
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;