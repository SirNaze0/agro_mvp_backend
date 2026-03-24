package com.agromvp.app_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "registro_valores",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_registro_valor_indicador", columnNames = {"registro_id", "indicador_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroValor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_uuid", nullable = false, unique = true)
    private UUID clientUuid;

    @ManyToOne
    @JoinColumn(name = "registro_id", nullable = false)
    private Registro registro;

    @ManyToOne
    @JoinColumn(name = "indicador_id", nullable = false)
    private Indicador indicador;

    @Column(name = "valor_numerico")
    private Double valorNumerico;

    @Column(name = "valor_texto")
    private String valorTexto;

    @Column(name = "valor_booleano")
    private Boolean valorBooleano;

    @Column(name = "valor_fecha")
    private LocalDate valorFecha;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}