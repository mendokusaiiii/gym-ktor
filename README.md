# Academia API

Uma API RESTful desenvolvida com **Ktor**, **Koin**, e **Kotlin** para gerenciar usuários e atividades de uma academia. Este projeto implementa autenticação JWT, integração com banco de dados H2 usando Exposed ORM, e uma arquitetura em camadas, demonstrando boas práticas para um backend moderno.

## Funcionalidades
- **Registro de Usuários**: Crie uma conta com nome de usuário, senha e e-mail.
- **Autenticação**: Faça login para obter um token JWT.
- **Gerenciamento de Atividades**: Crie e liste atividades (como treinos) associadas a um usuário.
- **Rotas Protegidas**: Acesse endpoints de atividades apenas com um token JWT válido.

## Tecnologias Utilizadas
- **Kotlin**: Linguagem principal.
- **Ktor**: Framework para construção da API.
- **Koin**: Injeção de dependências.
- **Exposed**: ORM para interação com o banco de dados.
- **H2 Database**: Banco de dados em memória para desenvolvimento.
- **JWT**: Autenticação baseada em tokens.
- **HikariCP**: Pool de conexões para o banco de dados.

## Pré-requisitos
- **JDK 17** ou superior.
- **Gradle** (gerenciado pelo projeto).
- **Ferramenta HTTP** (como Postman ou cURL) para testar a API.

## Como Configurar
1. Clone o repositório:
   ```bash
   git clone
   cd
   ```
2. Compile e baixe as dependências:
   ```bash
   ./gradlew build
   ```
3. Execute a aplicação:
   ```bash
   ./gradlew run
   ```
   A API estará disponível em `http://localhost:8080`.

## Como Usar a API
Use uma ferramenta como Postman ou cURL para interagir com os endpoints. Todos os exemplos assumem que a API está rodando em `http://localhost:8080`.

### 1. Registrar um Usuário
- **Método**: POST
- **Endpoint**: `/auth/register`
- **Body** (JSON):
  ```json
  {
    "username": "testuser",
    "password": "12345",
    "email": "test@example.com"
  }
  ```
- **Resposta** (201 Created):
  ```json
  {
    "id": 1,
    "username": "testuser",
    "password": "12345",
    "email": "test@example.com"
  }
  ```
- **Erro** (409 Conflict): "Username already exists"

### 2. Fazer Login
- **Método**: POST
- **Endpoint**: `/auth/login`
- **Body** (JSON):
  ```json
  {
    "username": "testuser",
    "password": "12345"
  }
  ```
- **Resposta** (200 OK):
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
  ```
- **Erro** (401 Unauthorized): "Invalid credentials"

### 3. Criar uma Atividade
- **Método**: POST
- **Endpoint**: `/activities`
- **Header**: `Authorization: Bearer <token>`
- **Body** (JSON):
  ```json
  {
    "name": "Treino de Peito",
    "description": "Foco em supino e crucifixo",
    "userId": 1
  }
  ```
- **Resposta** (201 Created):
  ```json
  {
    "id": 1,
    "name": "Treino de Peito",
    "description": "Foco em supino e crucifixo",
    "userId": 1
  }
  ```
- **Erro** (400 BadRequest): "Failed to create activity"
- **Erro** (403 Forbidden): "Unauthorized user"

### 4. Listar Atividades de um Usuário
- **Método**: GET
- **Endpoint**: `/activities?userId=1`
- **Header**: `Authorization: Bearer <token>`
- **Resposta** (200 OK):
  ```json
  [
    {
      "id": 1,
      "name": "Treino de Peito",
      "description": "Foco em supino e crucifixo",
      "userId": 1
    }
  ]
  ```
- **Erro** (400 BadRequest): "Missing userId"

## Estrutura do Projeto
```
/src/main/kotlin
├── Main.kt
│   
├── config
│   └── DatabaseConfig.kt
├── data
│   ├── models
│   │   ├── User.kt
│   │   └── Activity.kt
│   └── repositories
│       ├── UserRepository.kt
│       └── ActivityRepository.kt
├── di
│   └── AppModule.kt
├── routes
│   ├── AuthRoutes.kt
│   └── ActivityRoutes.kt
├── services
│   ├── AuthService.kt
│   └── ActivityService.kt
└── utils
    └── AuthUtils.kt
```

## Possíveis Melhorias
- Adicionar hashing de senhas com BCrypt.
- Implementar validação de entrada (ex.: e-mail válido, senha forte).
- Adicionar testes unitários e de integração.
- Suportar mais funcionalidades, como categorias de treinos ou agendamento.
- Fazer deploy em uma plataforma como Heroku ou Railway.

## Contribuição
Sinta-se à vontade para abrir issues ou enviar pull requests com melhorias!

## Licença
MIT License - veja o arquivo [LICENSE](LICENSE) para detalhes.
