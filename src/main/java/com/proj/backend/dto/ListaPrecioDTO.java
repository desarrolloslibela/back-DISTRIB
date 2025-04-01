package com.proj.backend.dto;

import com.proj.backend.model.ListaPrecio;
import com.proj.backend.model.TipoListaPrecio;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaPrecioDTO {

    private Long id;
    private TipoListaPrecio tipo;
    private Long proveedorId;
    private String proveedorNombre;
    private String observaciones;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public ListaPrecioDTO(ListaPrecio l) {
        this.id = l.getId();
        this.tipo = l.getTipo();
        this.proveedorId = l.getProveedor() != null ? l.getProveedor().getId() : null;
        this.proveedorNombre = l.getProveedor() != null ? l.getProveedor().getRazonSocial() : null;
        this.fechaDesde = l.getFechaDesde();
        this.fechaHasta = l.getFechaHasta();
        this.observaciones = l.getObservaciones();
    }
}