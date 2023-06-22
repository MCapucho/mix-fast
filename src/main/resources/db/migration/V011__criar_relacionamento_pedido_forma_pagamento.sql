ALTER TABLE tb_pedidos
    ADD FOREIGN KEY (forma_pagamento_codigo) REFERENCES tb_formas_pagamento(codigo);