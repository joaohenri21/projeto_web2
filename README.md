# Sistema de Gerenciamento de Centro de Treinamento - CT JW

Projeto desenvolvido para a disciplina de Programação Web II.

O sistema tem como objetivo gerenciar alunos, professores, turmas, matrículas, presenças e usuários de um centro de treinamento.

## Tecnologias utilizadas

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* Thymeleaf
* PostgreSQL
* Flyway
* JasperReports
* Tailwind CSS
* HTMX
* Maven

## Funcionalidades implementadas

### Alunos

* Cadastro de alunos
* Listagem de alunos
* Edição de alunos
* Visualização de detalhes
* Exclusão de alunos
* Busca por nome
* Paginação

### Professores

* Cadastro de professores
* Listagem de professores
* Edição de professores
* Visualização de detalhes
* Exclusão de professores
* Busca por nome
* Paginação

### Turmas

* Cadastro de turmas
* Associação da turma com professor
* Busca dinâmica de professor usando HTMX
* Listagem de turmas
* Edição de turmas
* Visualização de detalhes
* Exclusão de turmas
* Busca por nome
* Paginação

### Matrículas

* Matrícula de alunos em turmas
* Busca dinâmica de aluno
* Regra para impedir matrícula ativa duplicada do mesmo aluno na mesma turma
* Listagem de matrículas
* Cancelamento de matrícula

### Presenças

* Registro de presença por turma
* Listagem de alunos matriculados ativos na turma
* Registro de presença e falta
* Listagem de presenças registradas

### Usuários e segurança

* Login com Spring Security
* Logout
* Controle de acesso por perfil
* Perfil ADMIN
* Perfil PROFESSOR
* Cadastro de usuários pelo administrador
* Bloqueio de acesso a páginas restritas

### Relatórios

* Geração de relatório PDF com JasperReports
* Relatório individual por turma
* Sub-relatório com alunos matriculados na turma

### Tratamento de erros

* Página personalizada para erro 403
* Página personalizada para erro 404
* Página personalizada para erro 500
* Tratamento global de exceções
* Mensagens amigáveis para operações de sucesso, alerta e erro

### Organização visual

* Layout base com Thymeleaf
* Fragmento de header
* Fragmento de footer
* Fragmento de mensagens
* Reaproveitamento de estrutura visual entre páginas

## Perfis de acesso

### ADMIN

O usuário administrador pode acessar todas as funcionalidades do sistema, incluindo:

* Alunos
* Professores
* Turmas
* Matrículas
* Presenças
* Relatórios
* Usuários

### PROFESSOR

O usuário professor pode acessar:

* Alunos
* Turmas
* Matrículas
* Presenças
* Relatórios

O professor não pode acessar:

* Cadastro de usuários
* Cadastro de professores

## Usuário inicial

Ao iniciar o sistema pela primeira vez, é criado automaticamente um usuário administrador:

```text
Login: admin
Senha: 123456
```

## Banco de dados

O projeto utiliza PostgreSQL.

Configuração usada no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/academia
spring.datasource.username=postgres
spring.datasource.password=123456
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

O banco deve se chamar:

```text
academia
```

As tabelas são criadas pelo Flyway através das migrations localizadas em:

```text
src/main/resources/db/migration
```

## Como executar o projeto

1. Criar o banco de dados `academia` no PostgreSQL.

2. Conferir usuário e senha no arquivo:

```text
src/main/resources/application.properties
```

3. Executar o projeto pelo terminal:

```powershell
.\mvnw.cmd clean spring-boot:run
```

4. Acessar no navegador:

```text
http://localhost:8080
```

## Estrutura principal do projeto

```text
src/main/java/br/com/joaowalter/academia
├── config
├── controller
├── dto
├── exception
├── model
├── repository
└── service
```

```text
src/main/resources/templates
├── alunos
├── error
├── fragments
├── matriculas
├── presencas
├── professores
├── turmas
├── usuarios
└── index.html
```

## Observações

O sistema foi desenvolvido com foco em organização, reaproveitamento de código, validações básicas, controle de acesso e geração de relatório PDF com sub-relatório.

O projeto utiliza uma arquitetura em camadas, separando responsabilidades entre controllers, services, repositories, models, DTOs, configurações e tratamento de exceções.
