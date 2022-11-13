# MNF

A Spring Boot application that provides a REST API for managing projects.

## Requirements

- Docker https://docs.docker.com/get-docker/
- Docker compose (Comes with Docker Desktop) https://docs.docker.com/compose/install/
- Java >=11

## Exposed port

```
http://localhost:8089
```

## Swagger

```
http://localhost:8089/swagger
```

# Postgres

### Start

```bash
cd docker/local
docker compose up
```

### Stop

```bash
docker compose down
```

### Wiping volumes

```bash
docker compose down
docker volume rm mnf_postgres_data
docker compose up
```

# Keycloak

### Keycloak Token

A request to generate a Keycloak bearer token is available in the project root. \
[GetBearerToken.http](./GetBearerToken.http)
