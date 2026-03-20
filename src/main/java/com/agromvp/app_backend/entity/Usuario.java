package com.agromvp.app_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dni_o_correo", nullable = false, unique = true)
    private String dniOCorreo;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "biometria_habilitada", nullable = false)
    private Boolean biometriaHabilitada = false;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}