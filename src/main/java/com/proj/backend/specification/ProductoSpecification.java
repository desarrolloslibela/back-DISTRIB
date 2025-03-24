package com.proj.backend.specification;

import com.proj.backend.model.Producto;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class ProductoSpecification {

    public static Specification<Producto> filtrar(String nombre, Long tipoProductoId, Boolean activo) {
        return (Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (nombre != null && !nombre.isBlank()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%"));
            }

            if (tipoProductoId != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("tipoProducto").get("id"), tipoProductoId));
            }

            if (activo != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("activo"), activo));
            }

            return predicate;
        };
    }
}