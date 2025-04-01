
package com.proj.backend.repository;

import com.proj.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ListaPrecioRepository extends JpaRepository<ListaPrecio, Long> {
    List<ListaPrecio> findByTipo(TipoListaPrecio tipo);
    boolean existsByTipoAndProveedorAndFechaDesde(TipoListaPrecio tipo, Proveedor proveedor, LocalDate fechaDesde);
    List<ListaPrecio> findByFechaDesdeGreaterThanEqualAndFechaHastaLessThanEqual(LocalDate desde, LocalDate hasta);
    List<ListaPrecio> findByProveedorId(Long proveedorId);
}
