ALTER TABLE tb_pedidos_produtos
    ADD FOREIGN KEY (pedido_codigo) REFERENCES tb_pedidos(codigo);

ALTER TABLE tb_pedidos_produtos
    ADD FOREIGN KEY (produto_codigo) REFERENCES tb_produtos(codigo);