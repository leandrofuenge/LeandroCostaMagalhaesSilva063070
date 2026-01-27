# 🎵 Music API

API REST para gerenciamento de **artistas** e **álbuns musicais**, desenvolvida com **Spring Boot**, seguindo boas práticas de arquitetura, versionamento de API e uso de **SQL legado (native queries)**.

Este projeto foi pensado para servir como **base sólida de backend**, com fácil evolução para autenticação JWT, Docker e novos módulos.

## 📝 Dados de inscrição

- Nome: Leandro Costa Magalhaes Silva
- Vaga: Engenheiro da Computação - Sênior
- Processo Seletivo: PROCESSO SELETIVO CONJUNTO Nº001/2026/SEPLAG e demais Órgãos - Engenheiro da Computação- Sênior
- Data de entrega: 05/02/2026
- N° Inscrição : 16247

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Bean Validation (Jakarta Validation)
- Banco de Dados Relacional (PostgreSQL / MySQL)
- Docker
- Maven

---

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas: MVC


### 🔹 Destaques
- Uso de **DTOs** para isolamento da camada REST
- **SQL Nativo (legado)** nos repositórios
- **Projections** para consultas otimizadas
- **Versionamento da API (`/api/v1`)**
- **Exception Handling global**
- **Validações básicas nos requests**

---

## 📌 Funcionalidades

### 🎤 Artistas
- Criar artista
- Listar artistas
- Buscar artista por ID
- Atualizar artista

### 💿 Álbuns
- Criar álbum vinculado a um artista
- Listar álbuns
- Buscar álbum por ID
- Listar álbuns por artista
- Atualizar álbum

---

## 🔗 Endpoints

### 🎤 Artistas

POST /api/v1/artistas
GET /api/v1/artistas
GET /api/v1/artistas/{id}
PUT /api/v1/artistas/{id}


### 💿 Álbuns

POST /api/v1/albuns/artista/{artistaId}
GET /api/v1/albuns
GET /api/v1/albuns/{id}
GET /api/v1/albuns/artista/{artistaId}
PUT /api/v1/albuns/{id}

## 🚀 Como executar a aplicação

### 🔧 Pré-requisitos
- Docker
- Docker Compose

### ▶️ Comandos

```bash
docker compose up -d --build
docker compose logs -f
```

---

## 🧪 Como testar a aplicação

### 🔐 1. Liberação de Token (Autenticação)

Responsável por gerar o token JWT necessário para acessar os endpoints protegidos.

- **Método:** POST  
- **Endpoint:**  
```
http://localhost:7070/auth/token
```

#### Query Params

| Key | Value |
|----|------|
| user | usuario |

📌 **Observação:**  
O usuário deve copiar o **refresh token** retornado e utilizá-lo no header `Authorization` como `Bearer {TOKEN}` para acessar as funcionalidades da aplicação.

---

### 🎤 2. Criar Artista

- **Método:** POST  
- **Endpoint:**  
```
http://localhost:7070/api/v1/artistas
```

#### Headers

| Key | Value |
|----|------|
| Authorization | Bearer {TOKEN} |
| Content-Type | application/json |

#### Body (JSON)

```json
{
  "nome": "Bastille",
  "descricao": "Banda britânica de indie pop",
  "dataNascimento": "2010-01-01",
  "tipo": "BANDA",
  "paisOrigem": "Reino Unido",
  "website": "https://www.bastillebastille.com",
  "regionalId": 1
}
```

---

### 💿 3. Criar Álbum

Cria um álbum associado a um artista específico.

- **Método:** POST  
- **Endpoint:**  
```
http://localhost:7070/api/v1/albuns/artista/{artistaId}
```

#### Exemplo
```
http://localhost:7070/api/v1/albuns/artista/1
```

#### Headers

| Key | Value |
|----|------|
| Authorization | Bearer {TOKEN} |
| Content-Type | application/json |

#### Body (JSON)

```json
{
  "titulo": "Bad Blood",
  "anoLancamento": 2015,
  "tipo": "STUDIO",
  "gravadora": "Island Records",
  "descricao": "Álbum solo da banda Bastille",
  "numeroFaixas": 12,
  "preco": 39.50
}
```

---

### 🖼️ 4. Upload de Imagem da Capa

Permite o upload de uma ou mais imagens de capa do álbum.

- **Método:** POST  
- **Endpoint:**  
```
http://localhost:7070/api/v1/albuns/{albumId}/capas
```

#### Exemplo
```
http://localhost:7070/api/v1/albuns/1/capas
```

#### Headers

| Key | Value |
|----|------|
| Authorization | Bearer {TOKEN} |

#### Body (form-data)

| Key | Tipo | Descrição |
|----|----|-----------|
| files | File | Imagem da capa |
| prefix | Text | images |

---

### 📄 5. Paginação de Álbuns

Consulta paginada com ordenação configurável.

- **Método:** GET  
- **Endpoint:**  
```
http://localhost:7070/api/v1/albuns/paginacao
```

#### Query Params

| Key | Value |
|----|------|
| page | 0 |
| size | 5 |
| sort | titulo,asc |

#### Exemplo
```
http://localhost:7070/api/v1/albuns/paginacao?page=0&size=5&sort=titulo,asc
```

## 📊 Relatório da Aplicação

O projeto foi dividido em 7 fases


Fase 1 

A primeira fase foi realizada com a configuração básica e estrutura inicial, que consistiu em configurar o projeto Docker, definições iniciais e um README básico. Esta etapa não apresentou complexidades, pois trata-se de uma fase inicial do projeto, focada apenas na preparação do ambiente. Portanto, não houve dificuldades nesta fase, que foi implementada 100% conforme o planejado.

Fase 2

A segunda fase consistiu na modelagem do banco de dados, que envolveu a definição das entidades, a criação de migrations com Flyway e a configuração dos relacionamentos entre as tabelas. Esta etapa exigiu um nível maior de experiência, principalmente no processo de modelagem e na definição clara das entidades — que correspondem aos modelos de dados a serem aplicados no banco. A implementação desta fase foi concluída 100%, conforme o planejado.

Fase 3

A terceira fase foi dedicada à segurança e autenticação, incluindo a implementação de JWT com expiração, mecanismo de renovação de token (Renew Token), filtro de domínios e configuração básica de segurança (Basic Security Config). Nesta etapa, senti uma dificuldade significativa, pois foi minha primeira vez lidando com esse aspecto específico de segurança em Java Spring Boot. Passei aproximadamente um dia e meio tentando solucionar o bug e compreender o problema. Por fim, consegui resolver após estudar a documentação e entender como essa estrutura se aplica em um projeto Spring Boot. Esta fase foi implementada 100% conforme o planejado.

Fase 4

A quarta fase consistiu na implementação do CRUD básico (POST, PUT, GET), versionamento, validações simples e tratamento de exceções. Nesta etapa, não senti grandes dificuldades, pois já possuo experiência a partir de um projeto pessoal anterior que utilizava essas mesmas funcionalidades. Dessa forma, pude contar com uma base sólida para desenvolver esta parte do sistema. A fase foi implementada 100% conforme o planejado.

Fase 5 

A quinta fase compreendeu a implementação da paginação nas consultas de álbuns, consultas parametrizadas, busca por nome do artista com ordenação alfabética (ascendente e descendente), e a documentação dos endpoints com OpenAPI/Swagger.

Nesta etapa, enfrentei certa dificuldade inicial, especialmente em relação ao conceito de paginação, que não era claro para mim. Após estudar a documentação disponível, compreendi que a paginação é um tipo de consulta parametrizada que visa reduzir o uso de memória e processamento ao dividir os resultados em partes menores — um aspecto muito relevante para a eficiência do sistema.

Para as consultas, utilizei JPQL (Java Persistence Query Language), optando por essa abordagem por ser amplamente consolidada e por garantir um funcionamento mais preciso e controlado no contexto do projeto.

Quanto à documentação dos endpoints, como já tinha experiência anterior com Swagger, a adaptação e o entendimento do processo foram mais tranquilos.

Esta fase foi implementada 100%, atendendo a todos os requisitos definidos.


Fase 6 

A sexta fase abrangeu a integração com MinIO/S3, armazenamento das imagens no MinIO (via API S3), upload de uma ou mais imagens de capa do álbum e recuperação por links pré-assinados com expiração de 30 minutos. Nesta etapa, tive certa dificuldade inicial, pois não compreendia completamente como executar essas funções. Após estudar a documentação e analisar códigos de referência, consegui entender a lógica de implementação e como aplicar essas funcionalidades.

Foi muito importante para o projeto compreender o conceito do MinIO e sua aplicação prática. A funcionalidade de upload múltiplo — para uma ou mais imagens de capa por álbum — foi baseada em projetos existentes, adaptando-a ao contexto do Spring Boot.

Em relação à recuperação por links pré-assinados, essa parte ainda está em desenvolvimento, pois o link gerado na resposta é um endereço interno do Docker, inacessível diretamente pelo navegador. Estou trabalhando para resolver essa questão e garantir seu funcionamento adequado.

No momento, esta fase está 80% implementada.

Fase 7

Sétima fase — requisitos direcionados a nível sênior — que inclui Health Checks, Liveness/Readiness, testes unitários, WebSocket, Rate Limit e endpoints regionais.

Nesta etapa, foram implementados:

Health Checks

WebSocket para notificar o frontend a cada novo álbum cadastrado

Rate Limit: limite de até 10 requisições por minuto por usuário

Para desenvolver essas funcionalidades, precisei realizar pesquisas para entender as soluções e implementá-las adequadamente. Ao final, consegui concluir parte dos requisitos planejados.

Esta fase está 70% implementada.


Priorizei o desenvolvimento por fases para otimizar o desempenho e a organização do trabalho. O Produto Mínimo Viável (MVP) foi essencial nas etapas iniciais, permitindo obter rapidamente um produto funcional. O Spring Boot facilitou bastante o processo, gerenciando automaticamente muitas configurações técnicas.

A escalabilidade e a manutenibilidade foram preocupações centrais ao longo do planejamento. A divisão em fases permitiu que o sistema crescesse de forma ordenada e sustentável. A aplicação foi estruturada em uma arquitetura MVC monolítica, o que, aliado à abordagem incremental, possibilitou a introdução progressiva de camadas como segurança (Spring Security) sem a necessidade de reescrever a base do código existente.

Dessa forma, o projeto pode evoluir com consistência, mantendo a qualidade e permitindo futuras expansões de maneira controlada.
