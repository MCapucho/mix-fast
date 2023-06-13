# Mix Fast v.1.0.0

### Sobre

Sistema de autoatendimento de fast food, é composto por uma série de dispositivos e interfaces que permitem aos clientes
selecionar e fazer pedidos sem precisar interagir com um atendente.

### Como executar o sistema

No projeto existe um arquivo docker-compose.yml, para executar é necessário abrir o terminal na
pasta raiz do projeto e executar o comando:
- docker-compose up -d

### Endpoints

- Para ter acesso aos endpoints do sistema é possível utilizar o
[Swagger-UI](http://localhost:9080/swagger-ui/index.html) - (Obs: Para utilizar o swagger, o sistema precisa estar
executando).

### Tecnologias

- Java (version 17)
- Spring Boot (version 3.0.6)
- MySQL (version 8)

### Implementações do Sistema
- [X] Cadastro de cliente
- [X] Identificação do cliente via CPF
- [X] Criar, editar e remover de produto
- [X] Buscar produtos por categoria
- [X] Fake checkout, enviar os produtos escolhidos para a fila
- [X] Listar os pedidos