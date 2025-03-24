package com.proj.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoProductoDTO {
    private Long id;
    private String nombre;
}