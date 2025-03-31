package com.proj.backend.dto;

import com.proj.backend.model.Automotor;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AutomotorDTO {

    private Long id;
    private String patente;
    private String marca;
    private String modelo;
    private Boolean activo;
    private LocalDate fechaAlta;
    private String observaciones;

    public AutomotorDTO(Automotor a) {
        this.id = a.getId();
        this.patente = a.getPatente();
        this.marca = a.getMarca();
        this.modelo = a.getModelo();
        this.activo = a.getActivo();
        this.fechaAlta = a.getFechaAlta();
        this.observaciones = a.getObservaciones();
    }
}