package com.cetes.directo.calculadoras.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CetesDto {
    private String noTitulosCetes;
    private String tasaBrutaCetes;
    private String inversionCetes;
    private String noTitulosBonddia;
    private String tasaBrutaBonddia;
    private String inversionBonddia;
    private String interesBruto;
    private String isr;
    private String montoTotal;
}
