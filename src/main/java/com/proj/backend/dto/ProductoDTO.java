package com.proj.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;
    private String nombre;
    private Double capacidad;
    private Long tipoProductoId;
    private Boolean activo;
}