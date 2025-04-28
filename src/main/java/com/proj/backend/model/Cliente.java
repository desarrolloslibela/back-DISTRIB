package com.proj.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String razonSocial;

    @Column(nullable = false, unique = true)
    private String cuit;

    private String email;
    private String telefono;
    private String direccion;
    private Boolean activo = true;

    @Column(updatable = false)
    private LocalDateTime fechaAlta;

    private Double latitud;
    private Double longitud;

    @PrePersist
    public void prePersist() {
        this.fechaAlta = LocalDateTime.now();
    }
}