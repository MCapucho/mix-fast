## Mix Fast v.1.0.0

### Sobre

Sistema de autoatendimento de fast food, é composto por uma série de dispositivos e interfaces que permitem aos clientes
selecionar e fazer pedidos sem precisar interagir com um atendente.

### Como executar

No projeto existe um arquivo docker-compose.yml, para executar é necessário abrir o terminal na
pasta raiz do projeto e executar o comando:
- docker-compose up -d

### Endpoints

Para ter acesso aos endpoints do sistema é possível utilizar o
[Swagger-UI](http://localhost:9080/swagger-ui/index.html) 
- Para utilizar o swagger, executar a aplicação

### Tecnologias

- Java (version 17)
- Spring Boot (version 3.1.0)
- MySQL (version 8)

### Implementações do Sistema
- [X] Cadastro de cliente
- [X] Identificação do cliente via CPF
- [X] Criar, editar e remover de produto
- [X] Buscar produtos por categoria
- [X] Fake checkout, enviar os produtos escolhidos para a fila
- [X] Listar os pedidos

## Passos para utilização do sistema - Fluxo de pedido

### 1) Cadastrar Categoria
- http://localhost:9080/v1/categorias

### 2) Cadastrar Produto
- http://localhost:9080/v1/produtos

### 3) Cadastrar Cliente
- http://localhost:9080/v1/clientes

### 4) Cadastrar Formas de Pagamento
- http://localhost:9080/v1/formas_pagamento

### 5) Enviar pedido com/sem Cliente
- http://localhost:9080/v1/pedidos

### 6) Pedido
- http://localhost:9080/v1/pedidos/<codigo-do-pedido>/preparamento
- http://localhost:9080/v1/pedidos/<codigo-do-pedido>/entrega
- http://localhost:9080/v1/pedidos/<codigo-do-pedido>/finalizado

### Opcional (Somente quando o status for *RECEBIDO*)
* Cancelamento
  - http://localhost:9080/v1/pedidos/<codigo-do-pedido>/cancelamento



