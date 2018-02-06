package com.cetes.directo.calculadoras.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class Bondes {
    private double inversion;
    private double tasaCetes;
    private double tasaBondes;
    private double plazo;
    private double rendimiento;
    private double isr;
    private double total;
    private double montoBondes;
    private double tituloBondes;
    private double corteCupon;
    private double impuestoCorteCupon;
    private double remanenteCetes;
    private double montoCetes;
    private double titulosCetes;
    private double rendimientoCetes;
    private double impuestoCetes;
    private double remanenteBonddia;
    private double montoBonddia;
    private double rendimientoBonddia;
    private double remanentes;
    private double totalUltimo;
    private double interesBruto;
}
