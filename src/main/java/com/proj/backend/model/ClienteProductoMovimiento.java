package com.proj.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteProductoMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    private Producto producto;

    @Column(nullable = false)
    private LocalDateTime fechaMovimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovimientoTipo tipoMovimiento;

    @Column(nullable = false)
    private Integer cantidad;

    private String observacion;
}