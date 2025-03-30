package com.proj.backend.service.impl;

import com.proj.backend.dto.ProveedorDTO;
import com.proj.backend.model.Proveedor;
import com.proj.backend.repository.ProveedorRepository;
import com.proj.backend.service.ProveedorService;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProveedorDTO> listar() {
        return proveedorRepository.findAll().stream().map(ProveedorDTO::new).toList();
    }

    @Override
    public List<ProveedorDTO> filtrar(String razonSocial, String cuit, String direccion, String email, String telefono, Boolean activo, LocalDate fechaDesde, LocalDate fechaHasta) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Proveedor> cq = cb.createQuery(Proveedor.class);
        Root<Proveedor> root = cq.from(Proveedor.class);

        List<Predicate> predicates = new ArrayList<>();

        if (razonSocial != null && !razonSocial.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("razonSocial")), "%" + razonSocial.toLowerCase() + "%"));
        }
        if (cuit != null && !cuit.isBlank()) {
            predicates.add(cb.like(root.get("cuit"), "%" + cuit + "%"));
        }
        if (direccion != null && !direccion.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("direccion")), "%" + direccion.toLowerCase() + "%"));
        }
        if (email != null && !email.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }
        if (telefono != null && !telefono.isBlank()) {
            predicates.add(cb.like(root.get("telefono"), "%" + telefono + "%"));
        }
        if (activo != null) {
            predicates.add(cb.equal(root.get("activo"), activo));
        }
        if (fechaDesde != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("fechaAlta"), fechaDesde.atStartOfDay()));
        }
        if (fechaHasta != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("fechaAlta"), fechaHasta.atTime(23, 59, 59)));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList().stream().map(ProveedorDTO::new).toList();
    }

    @Override
    public Optional<ProveedorDTO> obtener(Long id) {
        return proveedorRepository.findById(id).map(ProveedorDTO::new);
    }

    @Override
    public ProveedorDTO crear(ProveedorDTO dto) {
        if (proveedorRepository.existsByCuit(dto.getCuit())) {
            throw new RuntimeException("Ya existe un proveedor con ese CUIT");
        }

        Proveedor proveedor = Proveedor.builder()
                .razonSocial(dto.getRazonSocial())
                .cuit(dto.getCuit())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .observaciones(dto.getObservaciones())
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .build();
        return new ProveedorDTO(proveedorRepository.save(proveedor));
    }

    @Override
    public ProveedorDTO actualizar(Long id, ProveedorDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        if (!proveedor.getCuit().equals(dto.getCuit()) && proveedorRepository.existsByCuit(dto.getCuit())) {
            throw new RuntimeException("Ya existe otro proveedor con ese CUIT");
        }

        proveedor.setRazonSocial(dto.getRazonSocial());
        proveedor.setCuit(dto.getCuit());
        proveedor.setEmail(dto.getEmail());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setObservaciones(dto.getObservaciones());
        proveedor.setActivo(dto.getActivo());

        return new ProveedorDTO(proveedorRepository.save(proveedor));
    }

    @Override
    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }
}