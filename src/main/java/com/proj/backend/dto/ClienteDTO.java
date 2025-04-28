package com.proj.backend.dto;

import com.proj.backend.model.Cliente;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {

    private Long id;
    private String razonSocial;
    private String cuit;
    private String email;
    private String telefono;
    private String direccion;
    private Boolean activo;
    private LocalDateTime fechaAlta;
    private Double latitud;
    private Double longitud;

    public ClienteDTO(Cliente c) {
        this.id = c.getId();
        this.razonSocial = c.getRazonSocial();
        this.cuit = c.getCuit();
        this.email = c.getEmail();
        this.telefono = c.getTelefono();
        this.direccion = c.getDireccion();
        this.activo = c.getActivo();
        this.fechaAlta = c.getFechaAlta();
        this.latitud = c.getLatitud();
        this.longitud = c.getLongitud();
    }
}