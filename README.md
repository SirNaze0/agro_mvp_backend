## Endpoints API (para integración con el Front)

Base URL:
- `http://localhost:8080` (o el puerto configurado en `PORT`; ver `server.port` en `application.properties`)

Nota: no hay configuración de autenticación en este backend; los endpoints son públicos.

### Obtener catálogo
- Método: `GET`
- Ruta: `/api/catalogo`
- Query params opcionales:
  - `usuarioId` (Long) -> si se envía, filtra productos e indicadores por asignaciones activas en `usuario_producto`.
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
- Query params opcionales:
  - `usuarioId` (Long) -> si se envía, devuelve solo productos activos asignados al usuario en `usuario_producto`.
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
- Query params opcionales:
  - `usuarioId` (Long) -> si se envía, filtra por tipo y por asignación activa en `usuario_producto`.
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
  "tipoIndicador": 1,
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
  "clientUuid": "550e8400-e29b-41d4-a716-446655440000 (opcional; si no se envía se genera)",
  "usuarioId": 1,
  "productoId": 2,
  "fechaRegistro": "2026-03-19",
  "horaRegistro": "10:15:00",
  "latitud": 0.0,
  "longitud": 0.0,
  "estadoRegistro": "PENDIENTE|COMPLETO|SINCRONIZADO|ERROR (opcional; default PENDIENTE)",
  "estadoFoto": "SIN_FOTO|PENDIENTE|SINCRONIZADA|ERROR (opcional; default SIN_FOTO)",
  "observaciones": "string (max 500 chars)",
  "valores": [
    {
      "clientUuid": "c56a4180-65aa-42ec-a945-5fd21dec0538 (opcional; si no se envía se genera)",
      "indicadorId": 10,
      "valorNumerico": 12.34
    }
  ]
}
```

Reglas relevantes para `POST /api/registros`:
- `clientUuid` del `RegistroCreateRequest` debe ser único (no puede existir otro registro con el mismo `clientUuid`).
- `productoId` debe estar asignado al `usuarioId` en `usuario_producto` y con `activo = true`.
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
  "estadoRegistro": "PENDIENTE",
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
