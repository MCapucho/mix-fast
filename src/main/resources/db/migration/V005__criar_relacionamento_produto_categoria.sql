ALTER TABLE tb_produtos
    ADD FOREIGN KEY (categoria_codigo) REFERENCES tb_categorias(codigo);