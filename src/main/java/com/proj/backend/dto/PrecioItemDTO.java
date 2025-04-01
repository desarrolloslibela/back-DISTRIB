package com.proj.backend.dto;

import com.proj.backend.model.PrecioItem;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PrecioItemDTO {
    private Long id;
    private Long listaPrecioId;
    private Long productoId;
    private BigDecimal precioNeto;
    private BigDecimal porcentajeIVA;
    private BigDecimal precioFinal;

    public PrecioItemDTO(PrecioItem item) {
        this.id = item.getId();
        this.listaPrecioId = item.getListaPrecio().getId();
        this.productoId = item.getProducto().getId();
        this.precioNeto = item.getPrecioNeto();
        this.porcentajeIVA = item.getPorcentajeIVA();
        this.precioFinal = item.getPrecioFinal();
    }
}