# TurismoApp REST API - Hoteles Pirenaicos con IA ⛰️🤖

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-brightgreen?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)
![Spring AI](https://img.shields.io/badge/Spring_AI-1.0.0--M5-6DB33F?style=for-the-badge&logo=spring)
![Llama 3](https://img.shields.io/badge/Meta_Llama_3.3-70B-0466C8?style=for-the-badge&logo=meta)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker)

API REST robusta desarrollada en **Java 21** y **Spring Boot** para la gestión de hoteles y reservas en la zona de los Pirineos. Diseñada con una arquitectura multicapa clara, seguridad integrada y **un asistente virtual nativo basado en Inteligencia Artificial (RAG)**.

## 🌟 Características Principales

* **Asistente IA con RAG (Retrieval-Augmented Generation):** Integración nativa de un chatbot conversacional que recomienda hoteles basándose **estrictamente en la base de datos local**.
* **Vectorización Local (Zero-Latency):** Uso de modelos *ONNX Transformers* ejecutados en local para convertir entidades de dominio en *Embeddings* matemáticos sin depender de APIs externas.
* **Memoria Conversacional:** El asistente mantiene el contexto del usuario durante la sesión mediante `InMemoryChatMemory` y `sessionStorage`.
* **Arquitectura Multicapa:** Separación estricta entre Controladores, Lógica de Negocio (Servicios) y Acceso a Datos (Repositorios).
* **Patrón DTO con Java Records:** Implementación ligera de Request/Response DTOs utilizando `Records` inmutables de Java 21.
* **Seguridad:** Rutas protegidas mediante **Spring Security** (Basic Auth), separando el modelo de autenticación del modelo de negocio.
* **Validaciones Robustas:** Uso de `Jakarta Validation` para asegurar la integridad de los datos.

## 🛠️ Stack Tecnológico

* **Core:** Java 21, Spring Boot 3.5
* **Inteligencia Artificial:** Spring AI, Groq API (Llama 3.3 70B), ONNX Transformers (Local Embeddings).
* **Persistencia:** Spring Data JPA, Hibernate, PostgreSQL + **PGVector**
* **Frontend:** Thymeleaf, HTML5, Bootstrap 5, Vanilla JS (Fetch API)
* **Herramientas:** Lombok, Maven

## 🐳 Instalación y Despliegue con Docker (Recomendado)

El proyecto está completamente preparado para ser orquestado mediante contenedores, gestionando tanto la base de datos (con soporte nativo para PGVector) como el backend.

### 1. Variables de Entorno (`.env`)
Para que el motor de IA y el servicio de correos funcionen, crea un archivo llamado `.env` en la raíz del proyecto y añade tus credenciales. Docker las inyectará automáticamente en el contenedor:
```env
GROQ_API_KEY=tu_clave_de_groq_aqui
MAIL=tu_correo@gmail.com
PASSWORD_MAIL=tu_contraseña_de_aplicacion
```

### 2. Compilar el Proyecto (Maven)
El `Dockerfile` utiliza un enfoque ligero basado en JRE de Ubuntu (`jammy`), por lo que necesita el archivo `.jar` previamente compilado. En la raíz del proyecto, ejecuta:
```
./mvnw clean package -DskipTests
```

### 3. Levantar los Contenedores
Una vez generado el archivo compilado en la carpeta `target/`, levanta toda la infraestructura con:
```
docker compose up -d --build
```
La aplicación estará disponible en `http://localhost:8080`.

### 4. Ingesta de Datos para la IA (Vectorización)
Para que el asistente virtual "BujarueloBot" pueda leer y recomendar los alojamientos, es necesario transformar las filas relacionales de PostgreSQL en vectores matemáticos. Con la aplicación arrancada, ejecuta una petición a este endpoint:
```
http://localhost:8080/api/chat/admin/ingestar
```**