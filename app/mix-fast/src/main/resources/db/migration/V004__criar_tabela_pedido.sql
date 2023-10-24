CREATE TABLE tb_pedidos (
    codigo varchar(255) not null primary key,
    fila int not null unique,
    data_pedido datetime not null,
    valor_total decimal(10, 2) not null,
    status varchar(20) not null,
    cliente_codigo varchar(255)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;