# **Movies API — Spring Boot + GraphQL**

API backend para la gestión de películas, actores, directores y reseñas, construida con Spring Boot y GraphQL, aplicando buenas prácticas de arquitectura, y mapeo con MapStruct.

## **Tecnologías**

- Java 17
- Spring Boot 3.x
- Spring GraphQL
- Spring Data JPA
- MapStruct
- Lombok
- SQL Server
- Docker

## **Arquitectura**

El proyecto sigue una arquitectura en capas:

```
resolver → service → repository → database
                ↓
              mapper
                ↓
               dto
```

## **Crear la red Docker (una sola vez)**
`docker network create movies-network`

## **Conectar el contenedor SQL Server a la red**
`docker network connect movies-network sql_server`

## **Construir la imagen**
`docker-compose build`

## **Levantar todo**
`docker-compose up -d`

### **Estructura de paquetes**

````
com.ochoa.yeisson.movies_api
│
├── config/          # Configuración (GraphQL, CORS)
├── dto/             # Data Transfer Objects
├── entities/        # Entidades JPA
├── exception/       # Manejo global de errores
├── mapper/          # MapStruct mappers
├── repository/      # Interfaces JPA
├── resolver/        # GraphQL Resolvers
├── service/         # Lógica de negocio (interfaces + impl)
├── validation/      # Funciones validadoras
└── enums/           # Enumeraciones
````

## **GraphQL**

Endpoint: http://localhost:8080/graphql

### **Ejemplo Query**

````
query {
    movies {
        id
        title
        director {
            name
        }
        actors {
            name
        }
    }
}
````

### **Ejemplo Mutation**

#### **Crear película**
````
mutation {
    createMovie(input: {
        title: "Inception",
        releaseYear: 2010
    }) {
        id
        title
    }
}
````

#### **Asignar actores a una película**
````
mutation {
    addActors(movieId: 1, actorIds: [1,2,3]) {
        id
        title
    }
}
````

#### **Asignar director**
````
mutation {
    assignDirector(movieId: 1, directorId: 1) {
        id
        title
    }
}
````

## **Configuración**

La configuración utilizada para la base de datos se encuentra en el archivo `application.properties`:

- **spring.datasource.url**: Correspondiente a la cadena de conexión a la base de datos
- **spring.datasource.username**: Correspondiente al usuario para conectarse al servidor de base de datos
- **spring.datasource.password**: Correspondiente a la contraseña asociada al usuario para la conexión al servidor de base de datos
- **spring.jpa.hibernate.ddl-auto**: Utilizado para aplicar los respectivos cambios en las tablas de base de datos, tras modificación de las entidades
- **spring.jpa.show-sql**: Utilizado para mostrar las sentencias SQL correspondiente a cada operación que realiza la API
- **spring.graphql.graphiql.enabled**: Utilizado para habilitar Graphiql y poder interactuar con las consultas de Graphql en el navegador **(Sólo para pruebas)**

**Nota**: Tener en cuenta que el host del servidor corresponde al nombre del contenedor donde esta la base de datos, por ejemplo: **sql_server**

## **Ejecución**

````
mvn clean install
mvn spring-boot:run
````