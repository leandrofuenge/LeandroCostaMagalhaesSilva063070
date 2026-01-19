# 🎵 Music API

API REST para gerenciamento de **artistas** e **álbuns musicais**, desenvolvida com **Spring Boot**, seguindo boas práticas de arquitetura, versionamento de API e uso de **SQL legado (native queries)**.

Este projeto foi pensado para servir como **base sólida de backend**, com fácil evolução para autenticação JWT, Docker e novos módulos.

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
