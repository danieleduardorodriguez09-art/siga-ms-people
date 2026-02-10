# Docker - SIGA People

Entorno Docker para levantar en otro equipo: **Oracle XE** y **siga-ms-people**. Todo está contenido en este repositorio.

## Requisitos

- Docker y Docker Compose v2
- Si el microservicio usa dependencias internas (Nexus), el equipo debe tener **acceso a ese Nexus** durante el `docker compose build`, o construir el JAR localmente (ver más abajo).

## Uso rápido

Desde el directorio raíz del proyecto (donde está `docker-compose.yml`):

```bash
docker compose up -d
```

- **Oracle**: puerto `1521` (usuario `SIGA_NEGOCIO` / `SIGA_NEGOCIO`, PDB: `XEPDB1`).
- **People**: http://localhost:9406

La primera vez, Oracle puede tardar **varios minutos** en estar listo; el microservicio espera a que pase el healthcheck.

## Solo Oracle

Para usar solo la base de datos (p. ej. conectar desde tu IDE):

```bash
docker compose up -d oracle
```

Conexión: `jdbc:oracle:thin:@//localhost:1521/XEPDB1`, usuario `SIGA_NEGOCIO`, contraseña `SIGA_NEGOCIO`.

## Build sin acceso al Nexus (recomendado si Docker no alcanza Nexus)

Si el build dentro de Docker falla por **Connect timed out** a `172.16.51.149:8081` (Nexus no accesible desde el contenedor):

1. **En tu máquina** (donde sí hay acceso a Nexus), genera el JAR:
   ```bash
   ./mvnw package -DskipTests
   ```
2. Crea la carpeta y copia el JAR:
   ```bash
   mkdir -p docker-build
   copy target\siga-ms-people-1.0.0-SNAPSHOT.jar docker-build\app.jar
   ```
   (En Linux/Mac: `cp target/siga-ms-people-1.0.0-SNAPSHOT.jar docker-build/app.jar`)
3. Construye la imagen **sin** ejecutar Maven dentro de Docker:
   ```bash
   docker build -f Dockerfile.jarlocal -t siga-ms-people:latest .
   ```
4. Para usar con `docker compose`: en `docker-compose.yml` puedes apuntar a esta imagen ya construida, o cambiar temporalmente `dockerfile: Dockerfile.jarlocal` y tener el JAR en `docker-build/app.jar` antes de `docker compose up --build`.

## Nexus en la red y HTTP

El build usa `.mvn/settings-docker.xml` para permitir repositorios HTTP (Maven 3.8.1+ los bloquea por defecto).  
Si Nexus está en tu máquina y el build en Docker no lo alcanza (`172.16.51.149`), en Windows/Mac puedes exponer la URL con `host.docker.internal`; para ello habría que usar un `pom.xml` o `settings` que tomen la URL de Nexus por variable (por ejemplo `NEXUS_URL`).

## Parar y eliminar

```bash
docker compose down
```

Para borrar también el volumen de datos de Oracle:

```bash
docker compose down -v
```
