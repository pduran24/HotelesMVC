TurismoApp REST API - Hoteles Pirenaicos

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-brightgreen?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-85EA2D?style=for-the-badge&logo=swagger)

API REST robusta desarrollada en **Java 21** y **Spring Boot** para la gestión de hoteles y reservas en la zona de los Pirineos. Diseñada con una arquitectura multicapa clara, seguridad integrada y lista para ser desplegada mediante contenedores.

## Características Principales

* **Arquitectura Multicapa:** Separación estricta entre Controladores, Lógica de Negocio (Servicios) y Acceso a Datos (Repositorios).
* **Patrón DTO con Java Records:** Implementación ligera de Request/Response DTOs utilizando `Records` inmutables de Java 21, evitando librerías de mapeo pesadas y protegiendo el modelo de dominio.
* **Seguridad:** Rutas protegidas mediante **Spring Security** (Basic Auth), separando el modelo de autenticación (`UserEntity`) del modelo de negocio (`Cliente`).
* **Validaciones Robustas:** Uso de `Jakarta Validation` para asegurar la integridad de los datos en los endpoints (validación de fechas lógicas, campos nulos, etc.).
* **Cálculo Dinámico:** La lógica de negocio abstrae la complejidad (ej. cálculo automático de días de reserva y precios totales basados en las fechas solicitadas).
* **Data Seeder Automático:** Precarga automática de una base de datos realista con alojamientos del Pirineo para facilitar el testeo inmediato.

## Stack Tecnológico

* **Core:** Java 21, Spring Boot 3.5
* **Persistencia:** Spring Data JPA, Hibernate, PostgreSQL
* **Seguridad:** Spring Security
* **Herramientas:** Lombok, Springdoc OpenAPI (Swagger), Maven
* **DevOps:** Docker, Docker Compose (Multi-stage build)

## Despliegue con Docker (Recomendado)

El proyecto está completamente dockerizado. No necesitas instalar Java, Maven ni PostgreSQL en tu máquina local para ejecutarlo.

1. Clona este repositorio.
2. Abre una terminal en la raíz del proyecto.
3. Ejecuta el siguiente comando para compilar y levantar los servicios:

```bash
docker compose up -d --build
