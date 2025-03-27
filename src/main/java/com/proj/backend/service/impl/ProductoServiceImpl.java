package com.proj.backend.service;

import com.proj.backend.dto.ProductoDTO;
import com.proj.backend.model.Producto;
import com.proj.backend.repository.ProductoRepository;
import com.proj.backend.repository.TipoProductoRepository;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final TipoProductoRepository tipoProductoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ProductoDTO> buscarConFiltros(String nombre, Long tipoProductoId, Double capacidad, Boolean activo, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producto> cq = cb.createQuery(Producto.class);
        Root<Producto> root = cq.from(Producto.class);

        List<Predicate> predicates = new ArrayList<>();

        if (nombre != null && !nombre.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }

        if (tipoProductoId != null) {
            predicates.add(cb.equal(root.get("tipoProducto").get("id"), tipoProductoId));
        }

        if (capacidad != null) {
            predicates.add(cb.equal(root.get("capacidad"), capacidad));
        }

        if (activo != null) {
            predicates.add(cb.equal(root.get("activo"), activo));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Producto> query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<ProductoDTO> content = query.getResultList().stream().map(ProductoDTO::new).toList();

        // Conteo corregido con nuevos predicates
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Producto> countRoot = countQuery.from(Producto.class);

        List<Predicate> countPredicates = new ArrayList<>();
        if (nombre != null && !nombre.isEmpty()) {
            countPredicates.add(cb.like(cb.lower(countRoot.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }
        if (tipoProductoId != null) {
            countPredicates.add(cb.equal(countRoot.get("tipoProducto").get("id"), tipoProductoId));
        }
        if (capacidad != null) {
            countPredicates.add(cb.equal(countRoot.get("capacidad"), capacidad));
        }
        if (activo != null) {
            countPredicates.add(cb.equal(countRoot.get("activo"), activo));
        }

        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Optional<ProductoDTO> obtenerPorId(Long id) {
        return productoRepository.findById(id).map(ProductoDTO::new);
    }

    @Override
    public ProductoDTO crear(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setCapacidad(dto.getCapacidad());
        producto.setActivo(dto.getActivo());

        if (dto.getTipoProductoId() != null) {
            producto.setTipoProducto(
                tipoProductoRepository.findById(dto.getTipoProductoId())
                    .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"))
            );
        }

        return new ProductoDTO(productoRepository.save(producto));
    }

    @Override
    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setCapacidad(dto.getCapacidad());
        producto.setActivo(dto.getActivo());

        if (dto.getTipoProductoId() != null) {
            producto.setTipoProducto(
                tipoProductoRepository.findById(dto.getTipoProductoId())
                    .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"))
            );
        }

        return new ProductoDTO(productoRepository.save(producto));
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}