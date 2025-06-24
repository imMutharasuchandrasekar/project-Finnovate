# 💼 Finnovate — A Modular Financial Solution

Finnovate is an financial application built using a modular **Microservices Architecture** with **Spring Boot**. It comprises three independent services, all containerized using Docker and orchestrated via Kubernetes for seamless deployment and scalability.

---

## 🌟 Key Features

- ⚙️ **Microservices Containerization**  
  All microservices are containerized using **Docker** and the images are hosted on **Docker Hub**, ensuring easy and consistent deployment.

- 🔧 **Centralized Configuration Management**  
  Production-grade configuration handling is achieved via **Spring Cloud Config Server**, allowing services to dynamically fetch environment-specific properties from an external repository — enabling seamless deployment across different environments.

- 🛡 **Resilience Patterns**  
  Implemented fault-tolerance mechanisms such as:
  - **Circuit Breaker**
  - **Retry**
  - **Rate Limiting**  
  These patterns help ensure high availability and robust error recovery for each microservice.

- 🧭 **Service Discovery & Communication**  
  - **Netflix Eureka Server** for service registry and discovery  
  - **Feign Client** for declarative and scalable inter-service communication

- ☸️ **Container Orchestration**  
  Hands-on implementation using **Kubernetes (k8s)** for:
  - Automated deployments
  - Service scaling
  - Distributed system management

---

## 🛠 Tech Stack

- **Backend:** Java, Spring Boot
- **Architecture:** Microservices
- **Database:** MySQL
- **Authentication & Authorization:** Keycloak (JWT, OAuth2)
- **DevOps & Infra:** Docker, Kubernetes, Spring Cloud Config, Eureka, Feign

---

## 📁 Repository Structure

