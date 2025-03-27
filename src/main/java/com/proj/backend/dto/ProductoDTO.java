package com.proj.backend.dto;

import com.proj.backend.model.Producto;

public class ProductoDTO {

    private Long id;
    private String nombre;
    private Double capacidad;
    private Boolean activo;
    private Long tipoProductoId;
    private String tipoProductoNombre;

    public ProductoDTO() {}

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.capacidad = producto.getCapacidad();
        this.activo = producto.getActivo();

        if (producto.getTipoProducto() != null) {
            this.tipoProductoId = producto.getTipoProducto().getId();
            this.tipoProductoNombre = producto.getTipoProducto().getNombre();
        } else {
            this.tipoProductoId = null;
            this.tipoProductoNombre = null;
        }
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getCapacidad() {
        return capacidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public Long getTipoProductoId() {
        return tipoProductoId;
    }

    public String getTipoProductoNombre() {
        return tipoProductoNombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidad(Double capacidad) {
        this.capacidad = capacidad;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setTipoProductoId(Long tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public void setTipoProductoNombre(String tipoProductoNombre) {
        this.tipoProductoNombre = tipoProductoNombre;
    }
}