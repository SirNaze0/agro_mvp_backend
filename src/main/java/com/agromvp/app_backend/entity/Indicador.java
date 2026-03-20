package com.agromvp.app_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "indicadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Indicador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "tipo_dato", nullable = false)
    private String tipoDato;

    @Column(name = "formula_referencia")
    private String formulaReferencia;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}