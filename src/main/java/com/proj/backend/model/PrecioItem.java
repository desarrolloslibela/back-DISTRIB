package com.proj.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PrecioItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ListaPrecio listaPrecio;

    @ManyToOne
    private Producto producto;

    private BigDecimal precioNeto;
    private BigDecimal porcentajeIVA;
    private BigDecimal precioFinal;

    @PrePersist
    @PreUpdate
    public void calcularFinal() {
        if (precioNeto != null && porcentajeIVA != null) {
            BigDecimal iva = precioNeto.multiply(porcentajeIVA).divide(BigDecimal.valueOf(100));
            this.precioFinal = precioNeto.add(iva);
        }
    }
}