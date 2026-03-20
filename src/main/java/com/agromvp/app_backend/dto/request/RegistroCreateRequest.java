package com.agromvp.app_backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RegistroCreateRequest {

    @NotNull(message = "clientUuid es obligatorio")
    private UUID clientUuid;

    @NotNull(message = "usuarioId es obligatorio")
    private Long usuarioId;

    @NotNull(message = "productoId es obligatorio")
    private Long productoId;

    @NotNull(message = "fechaRegistro es obligatoria")
    private LocalDate fechaRegistro;

    @NotNull(message = "horaRegistro es obligatoria")
    private LocalTime horaRegistro;

    private Double latitud;
    private Double longitud;

    @NotBlank(message = "estadoRegistro es obligatorio")
    private String estadoRegistro;

    @NotBlank(message = "estadoFoto es obligatorio")
    private String estadoFoto;

    @Size(max = 500, message = "observaciones no puede exceder 500 caracteres")
    private String observaciones;

    @NotEmpty(message = "Debe enviar al menos un valor de indicador")
    @Valid
    private List<RegistroValorRequest> valores;
}