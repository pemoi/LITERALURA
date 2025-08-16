# LiterAlura (Console App)
Catálogo de libros en consola con **Java 17 + Spring Boot 3 + PostgreSQL** consumiendo la API pública **Gutendex**.

## Funcionalidades
1. Buscar libro por título (API) y guardar si no existe.
2. Listar libros registrados.
3. Listar autores registrados.
4. Listar autores vivos en un año X.
5. Listar libros por idioma (`ES`, `EN`, `FR`, `PT`).

### Reglas
- No se permiten duplicados (por `gutenbergId` único).
- Si no hay resultados en la API, se informa al usuario.

## Requisitos
- Java 17, Maven 3.9+, PostgreSQL 14+ (o `docker-compose up -d`).

## Configuración
Edita `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Ejecutar
```bash
mvn spring-boot:run
```


## Extras añadidos
6. **Top N libros por descargas** (orden descendente por `download_count` guardado).
7. **Buscar libros por autor** (coincidencia parcial, insensible a mayúsculas).

