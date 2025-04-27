package com.proj.backend.dto;

import com.proj.backend.model.ListaPrecioVenta;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaPrecioVentaDTO {
    private Long id;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private String observaciones;

    public ListaPrecioVentaDTO(ListaPrecioVenta l) {
        this.id = l.getId();
        this.fechaDesde = l.getFechaDesde();
        this.fechaHasta = l.getFechaHasta();
        this.observaciones = l.getObservaciones();
    }
}