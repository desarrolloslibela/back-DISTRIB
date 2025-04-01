package com.proj.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ListaPrecio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoListaPrecio tipo;

    @ManyToOne
    private Proveedor proveedor;

    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    private String observaciones;

    @OneToMany(mappedBy = "listaPrecio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrecioItem> items;
}