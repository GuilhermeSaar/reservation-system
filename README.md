
# Sistema de Reservas para Restaurante (em desenvolvimento)

⚠️ Aviso: A aplicação foi dockerizada, porém a imagem Docker ainda não foi atualizada após as últimas modificações no código. Será necessário realizar um novo build para refletir as alterações mais recentes.

## Descrição do Projeto

Este projeto é um sistema backend para gerenciamento de reservas em um restaurante.

A aplicação permite que usuários se registrem, façam login e gerenciem reservas em mesas disponíveis. Utiliza autenticação via token JWT para proteger as rotas. Administradores podem gerenciar mesas. O projeto está documentado com Swagger e preparado para execução via Docker.

---

## Tecnologias Utilizadas

- Java 17  
- Spring Boot  
- Spring Security  
- JWT (JSON Web Token)  
- JPA / Hibernate  
- Maven  
- Swagger (springdoc-openapi)  
- Docker  
- PostgreSQL  

---

## Funcionalidades Implementadas

### Autenticação e Usuários

- Cadastro de usuário com nome, email e senha.
- Senha criptografada com BCrypt.
- Login com retorno de token JWT.
- Verificação de email duplicado no registro.

### Mesas

- Listagem de todas as mesas e seus status.
- Criação de nova mesa (apenas administradores).
- Atualização de mesa existente por ID.
- Remoção de mesa (apenas administradores).

### Reservas

- Criação de nova reserva com validação de disponibilidade e capacidade.
- Listagem de reservas do usuário autenticado.
- Cancelamento de reservas ativas.

---

## Endpoints Disponíveis

| Método | URL                         | Descrição                                        | Protegido?          |
|--------|-----------------------------|--------------------------------------------------|---------------------|
| POST   | /register                   | Registrar novo usuário                           | Não                 |
| POST   | /auth                       | Login e geração do token JWT                     | Não                 |
| GET    | /tables                     | Listar todas as mesas e seus status              | Não                 |
| POST   | /tables/new                 | Adicionar nova mesa                              | Sim (ADMIN)         |
| PUT    | /tables/update/:id          | Atualizar informações de uma mesa                | Sim (ADMIN)         |
| DELETE | /tables/delete/:id          | Remover mesa                                     | Sim (ADMIN)         |
| POST   | /reservations/new           | Criar nova reserva com validações                | Sim (CUSTOMER)      |
| GET    | /reservations               | Listar reservas do usuário autenticado           | Sim (CUSTOMER)      |
| PUT    | /reservations/:id/cancel    | Cancelar uma reserva ativa                       | Sim (CUSTOMER)      |

---

## Documentação da API (Swagger)

A API está documentada usando o Swagger (via SpringDoc OpenAPI).

Após iniciar o projeto, acesse:  
📄 **`http://localhost:8080/swagger-ui.html`** ou **`/swagger-ui/index.html`**

---

## Testando as Funcionalidades

### Registro

Envie uma requisição `POST` para `/register` com o corpo:

```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senha123"
}
```

### Login com ADM

Envie uma requisição `POST` para `/auth` com o corpo:

```json
{
  "email": "admin@gmail.com",
  "password": "admin"
}
```

O retorno será um token JWT. Utilize-o no cabeçalho das próximas requisições protegidas:

```
Authorization: Bearer SEU_TOKEN
```

---

## Executando com Docker

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repo.git
   cd seu-repo
   ```

2. Edite as variáveis de ambiente (se necessário) no `docker-compose.yml`.

3. Execute:
   ```bash
   docker-compose up --build
   ```

4. A aplicação estará disponível em `http://localhost:8080`

---

---

## Autor

Guilherme Saar  
📧 [oguisaar@gmail.com]  
🔗 [https://www.linkedin.com/public-profile/settings?lipi=urn%3Ali%3Apage%3Ad_flagship3_profile_self_edit_contact-info%3Bg1K425ZPTzGN2SgrjCLHrw%3D%3D]  
