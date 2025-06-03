# Sistema de Reservas para Restaurante

## Descrição do Projeto

Este projeto é um sistema backend para gerenciamento de reservas em um restaurante. Atualmente, a aplicação permite que usuários se registrem e façam login, recebendo um token JWT para autenticação nas próximas etapas.

O backend é desenvolvido em Java com Spring Boot, usando JWT para autenticação segura.

---

## Tecnologias Utilizadas

- Java 17  
- Spring Boot  
- Spring Security  
- JWT (JSON Web Token)  
- JPA / Hibernate  
- Maven  

---

## Funcionalidades Implementadas

### Registro de Usuário

- Cadastro de usuário com nome, email e senha.
- Senha criptografada usando BCrypt.
- Verificação para evitar cadastro com email duplicado.

### Login de Usuário

- Autenticação via email e senha.
- Retorno de token JWT para uso em autenticação nas próximas requisições.

---

## Endpoints Disponíveis

| Método | URL       | Descrição                    | Protegido? |
|--------|-----------|------------------------------|------------|
| POST   | /register | Registrar novo usuário       | Não        |
| POST   | /auth     | Login e geração do token JWT | Não        |

---

## Testando as Funcionalidades

### Registro

Envie uma requisição POST para `/register` com o corpo JSON:

```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senha123"
}
```
### Login

```json
{
  "email": "joao@email.com",
  "password": "senha123"
}
```

