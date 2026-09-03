# User Management API

## Sobre o projeto

API REST desenvolvida em Java utilizando o framework Spring Boot, colocando em prática conceitos de backend como autenticação e autorização com tokens JWT. A API utiliza PostgreSQL como banco de dados e Docker para a containerização da aplicação.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- Docker
- Maven

## Funcionalidades

- Cadastro de usuários
- Autenticação de usuários
- Geração de tokens JWT
- Autorização baseada em roles
- Criptografia das senhas utilizando BCrypt
- Persistência de dados utilizando PostgreSQL
- Execução da aplicação utilizando Docker

## Autenticação

A autenticação é realizada através de tokens JWT. Após realizar o login, a API gera um token que deve ser enviado nas requisições às rotas protegidas através do header `Authorization`.

Exemplo:

```text
Authorization: Bearer SEU_TOKEN
```

## Banco de dados

A aplicação utiliza **PostgreSQL** como banco de dados para persistência das informações dos usuários.

A comunicação entre a aplicação e o banco de dados é realizada utilizando **Spring Data JPA**, com o **Hibernate** como implementação do JPA.

Os dados dos usuários são armazenados na tabela `usuario`, contendo informações como:

* Nome
* Data de nascimento
* E-mail
* Senha criptografada
* Função (`USER` ou `ADMIN`)

A criação e atualização da estrutura da tabela são gerenciadas pelo Hibernate através da configuração do JPA.

Durante a execução com Docker, o PostgreSQL é executado em um container separado da API, utilizando uma rede Docker para permitir a comunicação entre os containers. Os dados do banco são armazenados em um volume Docker, permitindo que eles sejam preservados mesmo quando o container do PostgreSQL é reiniciado ou recriado.

## Endpoints

### Autenticação

| Método | Endpoint       | Autenticação | Descrição                                  |
| ------ | -------------- | ------------ | ------------------------------------------ |
| `POST` | `/auth/signup` |  Não        | Cadastra um novo usuário                   |
| `POST` | `/auth/login`  |  Não        | Autentica o usuário e retorna um token JWT |

### Usuários

| Método   | Endpoint         | Autenticação | Descrição                       |
| -------- | ---------------- | ------------ | ------------------------------- |
| `GET`    | `/usuarios`      |  JWT       | Retorna os usuários cadastrados |
| `GET`    | `/usuarios/{id}` |  JWT       | Retorna um usuário específico   |
| `POST`   | `/usuarios`      |  JWT       | Cadastra um usuário             |
| `PUT`    | `/usuarios/{id}` |  JWT       | Atualiza um usuário             |
| `DELETE` | `/usuarios/{id}` |  JWT       | Remove um usuário               |



## Como executar

1. Certifique-se de que o Docker esteja instalado e em execução.
2. Execute o arquivo `Inicialização.bat`.
3. A aplicação será iniciada na porta `8080`.

A API estará disponível em:

http://localhost:8080
