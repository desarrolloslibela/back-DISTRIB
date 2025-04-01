package com.proj.backend.repository;

import com.proj.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecioItemRepository extends JpaRepository<PrecioItem, Long> {
    boolean existsByListaPrecioAndProducto(ListaPrecio lista, Producto producto);
}