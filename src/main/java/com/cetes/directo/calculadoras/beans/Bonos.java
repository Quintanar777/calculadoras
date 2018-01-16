package com.cetes.directo.calculadoras.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class Bonos {
    private double inversion;
    private double tasaCetes;
    private double plazo;
    private double rendimiento;
    private double isr;
    private double total;
    private double montoBonos;
    private double tituloBonos;
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
    private double reinversionCC;
}
