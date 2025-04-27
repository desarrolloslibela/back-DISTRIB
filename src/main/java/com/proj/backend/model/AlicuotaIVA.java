package com.proj.backend.model;

import lombok.Getter;

@Getter
public enum AlicuotaIVA {
    IVA_0(0),
    IVA_10_5(10.5),
    IVA_21(21);

    private final double porcentaje;

    AlicuotaIVA(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}