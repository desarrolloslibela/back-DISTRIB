package com.proj.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrecioItemVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ListaPrecioVenta lista;

    @ManyToOne
    private Producto producto;

    private BigDecimal precioFinal;

    @Enumerated(EnumType.STRING)
    private AlicuotaIVA alicuotaIVA;

    private BigDecimal precioNeto;
    private BigDecimal iva;

    @PrePersist
    @PreUpdate
    public void calcularValores() {
        if (precioFinal != null && alicuotaIVA != null) {
            BigDecimal divisor = BigDecimal.valueOf(1 + alicuotaIVA.getPorcentaje() / 100);
            this.precioNeto = precioFinal.divide(divisor, 2, RoundingMode.HALF_UP);
            this.iva = precioFinal.subtract(this.precioNeto).setScale(2, RoundingMode.HALF_UP);
        }
    }
}