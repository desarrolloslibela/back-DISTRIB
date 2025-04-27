package com.proj.backend.dto;

import com.proj.backend.model.AlicuotaIVA;
import com.proj.backend.model.PrecioItemVenta;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrecioItemVentaDTO {
    private Long id;
    private Long listaId;
    private Long productoId;
    private BigDecimal precioFinal;
    private AlicuotaIVA alicuotaIVA;
    private BigDecimal precioNeto;
    private BigDecimal iva;

    public PrecioItemVentaDTO(PrecioItemVenta item) {
        this.id = item.getId();
        this.listaId = item.getLista().getId();
        this.productoId = item.getProducto().getId();
        this.precioFinal = item.getPrecioFinal();
        this.alicuotaIVA = item.getAlicuotaIVA();
        this.precioNeto = item.getPrecioNeto();
        this.iva = item.getIva();
    }
}