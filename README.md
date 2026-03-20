# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.agromvp.app-backend' is invalid and this project uses 'com.agromvp.app_backend' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.0.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.0.3/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.0.3/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/4.0.3/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Flyway Migration](https://docs.spring.io/spring-boot/4.0.3/how-to/data-initialization.html#howto.data-initialization.migration-tool.flyway)
* [Validation](https://docs.spring.io/spring-boot/4.0.3/reference/io/validation.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/4.0.3/reference/using/devtools.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

### Maven Parent overrides
ss
Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent thsis, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

## Endpoints API (para integración con el Front)

Base URL:
- `http://localhost:8080` (o el puerto configurado en `PORT`; ver `server.port` en `application.properties`)

Nota: no hay configuración de autenticación en este backend; los endpoints son públicos.

### Obtener catálogo
- Método: `GET`
- Ruta: `/api/catalogo`
- Respuesta: `200 OK`
- Body: `CatalogoResponse`

`CatalogoResponse`:
```json
{
  "tiposProducto": [],
  "productos": [],
  "productoIndicadores": []
}
```

### Obtener tipos de producto
- Método: `GET`
- Ruta: `/api/tipos-producto`
- Respuesta: `200 OK`
- Body: `TipoProductoResponse[]`

`TipoProductoResponse`:
```json
{
  "id": 0,
  "codigo": "string",
  "nombre": "string",
  "descripcion": "string",
  "activo": true
}
```

### Obtener productos activos
- Método: `GET`
- Ruta: `/api/productos`
- Respuesta: `200 OK`
- Body: `ProductoResponse[]`

`ProductoResponse`:
```json
{
  "id": 0,
  "codigo": "string",
  "nombre": "string",
  "descripcion": "string",
  "imagenUrl": "string",
  "activo": true,
  "tipoProductoId": 0,
  "tipoProductoCodigo": "string",
  "tipoProductoNombre": "string"
}
```

### Obtener productos por tipo
- Método: `GET`
- Ruta: `/api/productos/por-tipo/{tipoProductoId}`
- Path params:
  - `tipoProductoId` (Long)
- Respuesta: `200 OK`
- Body: `ProductoResponse[]`

### Obtener indicadores por producto
- Método: `GET`
- Ruta: `/api/indicadores/por-producto/{productoId}`
- Path params:
  - `productoId` (Long)
- Respuesta: `200 OK`
- Body: `IndicadorResponse[]`

`IndicadorResponse`:
```json
{
  "id": 0,
  "codigo": "string",
  "nombre": "string",
  "descripcion": "string",
  "unidadMedida": "string",
  "tipoDato": "NUMERICO|TEXTO|BOOLEANO|FECHA",
  "formulaReferencia": "string",
  "activo": true
}
```

### Crear registro
- Método: `POST`
- Ruta: `/api/registros`
- Headers:
  - `Content-Type: application/json`
- Respuesta:
  - `201 Created`
  - Body: `RegistroResponse`

`RegistroCreateRequest` (body):
```json
{
  "clientUuid": "550e8400-e29b-41d4-a716-446655440000",
  "usuarioId": 1,
  "productoId": 2,
  "fechaRegistro": "2026-03-19",
  "horaRegistro": "10:15:00",
  "latitud": 0.0,
  "longitud": 0.0,
  "estadoRegistro": "BORRADOR|COMPLETO|ERROR",
  "estadoFoto": "SIN_FOTO|PENDIENTE|SINCRONIZADA",
  "observaciones": "string (max 500 chars)",
  "valores": [
    {
      "clientUuid": "c56a4180-65aa-42ec-a945-5fd21dec0538",
      "indicadorId": 10,
      "valorNumerico": 12.34
    }
  ]
}
```

Reglas relevantes para `POST /api/registros`:
- `clientUuid` del `RegistroCreateRequest` debe ser único (no puede existir otro registro con el mismo `clientUuid`).
- `valores` debe contener al menos 1 elemento.
- En cada elemento de `valores[]`:
  - `clientUuid` debe ser único para el valor (no puede existir otro valor con el mismo `clientUuid`).
  - `indicadorId` debe estar asociado al `productoId` (indicador válido para ese producto).
  - El campo requerido depende de `indicador.tipoDato`:
    - `NUMERICO` -> `valorNumerico` (no puede ser `null`)
    - `TEXTO` -> `valorTexto` (no puede ser `null` ni `""`)
    - `BOOLEANO` -> `valorBooleano` (no puede ser `null`)
    - `FECHA` -> `valorFecha` (no puede ser `null`)
- `latitud` (si viene) debe estar entre `-90` y `90`.
- `longitud` (si viene) debe estar entre `-180` y `180`.

Formato de fechas/horas:
- `fechaRegistro` (LocalDate): `"yyyy-MM-dd"`
- `horaRegistro` (LocalTime): `"HH:mm:ss"`
- `valorFecha` (LocalDate): `"yyyy-MM-dd"`
- `createdAt` / `updatedAt` (LocalDateTime) / `timestamp` (errores): formato ISO-8601 por defecto de Jackson.

`RegistroResponse`:
```json
{
  "id": 0,
  "clientUuid": "550e8400-e29b-41d4-a716-446655440000",
  "usuarioId": 1,
  "productoId": 2,
  "fechaRegistro": "2026-03-19",
  "horaRegistro": "10:15:00",
  "latitud": 0.0,
  "longitud": 0.0,
  "estadoRegistro": "BORRADOR",
  "estadoFoto": "SIN_FOTO",
  "observaciones": "string",
  "deleted": false,
  "createdAt": "2026-03-19T10:15:30.000",
  "updatedAt": "2026-03-19T10:15:30.000"
}
```

### Errores comunes (formato estándar)
En errores, la API responde con `ApiErrorResponse`:
```json
{
  "timestamp": "2026-03-19T10:15:30.000",
  "status": 400,
  "error": "BAD_REQUEST|VALIDATION_ERROR|NOT_FOUND|INTERNAL_SERVER_ERROR",
  "message": "string",
  "details": []
}
```

Resumen de códigos:
- `400 Bad Request`: validaciones de negocio (`BadRequestException`) y errores de validación de request.
- `404 Not Found`: entidad no encontrada.
- `500 Internal Server Error`: errores inesperados.
