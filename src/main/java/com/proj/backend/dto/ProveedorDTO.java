package com.proj.backend.dto;

import com.proj.backend.model.Proveedor;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorDTO {

    private Long id;
    private String razonSocial;
    private String cuit;
    private String email;
    private String telefono;
    private String direccion;
    private String observaciones;
    private Boolean activo;
    private LocalDateTime fechaAlta;

    public ProveedorDTO(Proveedor p) {
        this.id = p.getId();
        this.razonSocial = p.getRazonSocial();
        this.cuit = p.getCuit();
        this.email = p.getEmail();
        this.telefono = p.getTelefono();
        this.direccion = p.getDireccion();
        this.observaciones = p.getObservaciones();
        this.activo = p.getActivo();
        this.fechaAlta = p.getFechaAlta();
    }
}