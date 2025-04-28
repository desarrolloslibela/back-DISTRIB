package com.proj.backend.dto;

import com.proj.backend.model.ClienteProductoMovimiento;
import com.proj.backend.model.MovimientoTipo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteProductoMovimientoDTO {

    private Long id;
    private Long clienteId;
    private String clienteRazonSocial;
    private Long productoId;
    private String productoNombre;
    private LocalDateTime fechaMovimiento;
    private MovimientoTipo tipoMovimiento;
    private Integer cantidad;
    private String observacion;

    public ClienteProductoMovimientoDTO(ClienteProductoMovimiento m) {
        this.id = m.getId();
        this.clienteId = m.getCliente().getId();
        this.clienteRazonSocial = m.getCliente().getRazonSocial();
        this.productoId = m.getProducto().getId();
        this.productoNombre = m.getProducto().getNombre();
        this.fechaMovimiento = m.getFechaMovimiento();
        this.tipoMovimiento = m.getTipoMovimiento();
        this.cantidad = m.getCantidad();
        this.observacion = m.getObservacion();
    }
}