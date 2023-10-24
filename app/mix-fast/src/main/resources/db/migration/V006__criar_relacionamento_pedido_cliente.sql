ALTER TABLE tb_pedidos
    ADD FOREIGN KEY (cliente_codigo) REFERENCES tb_clientes(codigo);