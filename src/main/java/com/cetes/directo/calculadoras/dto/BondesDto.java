package com.cetes.directo.calculadoras.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BondesDto {
    private String noTitulosCetes;
    private String tasaBrutaCetes;
    private String inversionCetes;
    private String noTitulosBondes;
    private String tasaBrutaBondes;
    private String inversionBondes;
    private String interesBruto;
    private String isr;
    private String montoTotal;
}
