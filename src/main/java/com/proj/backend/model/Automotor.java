package com.proj.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Automotor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String patente;

    private String marca;
    private String modelo;
    private Boolean activo = true;

    @Column(updatable = false)
    private LocalDate fechaAlta;

    private String observaciones;

    @PrePersist
    public void prePersist() {
        this.fechaAlta = LocalDate.now();
    }
}