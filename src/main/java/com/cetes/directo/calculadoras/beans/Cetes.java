package com.cetes.directo.calculadoras.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class Cetes {
    private double noTitulosCetes;
    private double tasaBruta;
    private double inversion;
    private double noTitulosBonddia;
    private double tasaBrutaBonddia;
    private double inversionBonddia;
    private double interesBruto;
    private double isr;
    private double montoTotal;
    private double montoInicial;
}
