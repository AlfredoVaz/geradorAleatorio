# Random Activities API

API Spring Boot para gerar, listar e consultar atividades aleatórias, com autenticação Basic, persistência H2 em memória e documentação via Swagger (springdoc).

## Requisitos
- Java 21
- Maven 3.9+

## Executando a aplicação
```bash
./mvnw spring-boot:run
```
Ou para build completo:
```bash
./mvnw clean package
java -jar target/geradorAleatorio-0.0.1-SNAPSHOT.jar
```

## Autenticação
- Método: Basic Auth
- Usuário: `admin`
- Senha: `1234`
- Todas as rotas (exceto `/actuator/health`, `/h2-console/**` e Swagger) exigem role `ADMIN`.

## Swagger / OpenAPI
- UI: `http://localhost:8080/swagger-ui.html`
- JSON: `http://localhost:8080/v3/api-docs`
- Use o botão “Authorize” no Swagger e informe `admin` / `1234` (Basic Auth).

## H2 Console (Banco em memória)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:randomdb`
- User: `sa`
- Password: (vazio)
- Observação: por ser em memória, os dados são perdidos a cada restart do app.

## Configurações principais (`src/main/resources/application.properties`)
- URL da API externa (Bored API):
  ```
  external.bored.random-url=https://bored-api.appbrewery.com/random
  ```
- H2 + JPA: já configurados para uso em memória e console habilitado.
- Tratativas globais de erro:
  - 404 para rotas inexistentes
  - 401 para autenticação ausente/inválida (JSON padronizado)

## Endpoints
Base: `http://localhost:8080`

- Gerar/retornar atividade aleatória (e persistir se nova):
  - `GET /randomActivities`
- Listar todas as atividades salvas:
  - `GET /randomActivities/list`
- Buscar atividade por chave (key):
  - `GET /randomActivities/list/{key}`

### Exemplos (curl)
- Gerar/consultar uma atividade aleatória:
```bash
curl -u admin:1234 http://localhost:8080/randomActivities
```
- Listar todas:
```bash
curl -u admin:1234 http://localhost:8080/randomActivities/list
```
- Buscar por key:
```bash
curl -u admin:1234 http://localhost:8080/randomActivities/list/8731710
```
- Rota inexistente (404 JSON):
```bash
curl -u admin:1234 http://localhost:8080/rota-inexistente
```
- Sem credenciais (401 JSON):
```bash
curl http://localhost:8080/randomActivities
```

## Testes
```bash
./mvnw test
```
Os testes de endpoint usam MockMvc e um mock do repositório externo.

## Estrutura
- `controller/` — Endpoints REST
- `service/` — Regras de negócio
- `repository/` — Consumo HTTP externo e JPA (H2)
- `model/` — Entidade `RandomActivity` (Tabela `ACTIVITY`)
- `dto/` — `RandomActivityDto` (contrato de resposta)
- `converter/` — Conversões entre entidade e DTO
- `config/` — Segurança (Basic Auth), entrypoint 401, liberação de H2 e Swagger
- `exception/` — Exceções e handler global (404/401/500/erros externos)

## Notas
- Chave lógica para evitar duplicidade: `ACTIVITY_KEY`.
- `ErrorResponse` no handler global inclui `timestamp`, `status`, `error`, `message`, `path`.
