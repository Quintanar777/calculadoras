package com.cetes.directo.calculadoras.business;

import com.cetes.directo.calculadoras.beans.Cetes;
import com.cetes.directo.calculadoras.dto.CetesDto;
import com.cetes.directo.calculadoras.util.CommonsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CetesDelegate {

    @Value("${calc.cetes.precio}")
    private Double valorCete;

    //tasas CETES
    @Value("${calc.cetes.tasa.28}")
    private Double tasa28;
    @Value("${calc.cetes.tasa.91}")
    private Double tasa91;
    @Value("${calc.cetes.tasa.182}")
    private Double tasa182;
    @Value("${calc.cetes.tasa.360}")
    private Double tasa360;

    @Value("${calc.bonddia.precio}")
    private Double valorBonddia;
    @Value("${calc.bonddia.tasa}")
    private Double tasaBonddia;

    @Autowired
    private Cetes cetes;

    public CetesDelegate() {
    }

    /**
     * Calcular cetes
     * */
    public CetesDto calcCetes(double monto, int plazo){
        double tasaCetes=0;
        if (plazo==28) tasaCetes= tasa28;
        if (plazo==91) tasaCetes= tasa91;
        if (plazo==182) tasaCetes= tasa182;
        if (plazo==360) tasaCetes= tasa360;

        double arreglo[] = new double [15];

        tasaCetes = CommonsUtil.round(tasaCetes/100,7);
        tasaBonddia = CommonsUtil.round(tasaBonddia/100,7);

        double precioXcete = CommonsUtil.round(valorCete/(1+(plazo*tasaCetes)/360),7);

        double titulos = CommonsUtil.round(monto/precioXcete,7);

        long iPart = (long) titulos;
        double fPart = titulos - iPart;

        arreglo[0] = monto;

        double montoRealCetes = CommonsUtil.round(iPart*precioXcete,7);

        arreglo[1] = CommonsUtil.round(montoRealCetes,2);

        double remanente = CommonsUtil.round((monto - (iPart*precioXcete)),7);
        double intBrutosCetes = CommonsUtil.round(((tasaCetes*100)/36000)*plazo*montoRealCetes,7);

        arreglo[2] = CommonsUtil.round(intBrutosCetes,2);

        double titulosBondia = CommonsUtil.round(remanente/valorBonddia,7);
        long tBondia = (long) titulosBondia;
        double fBondia = CommonsUtil.round(titulosBondia - tBondia,7);

        double montoRealBonddia = CommonsUtil.round((tBondia*valorBonddia),7);
        arreglo[3] = CommonsUtil.round(montoRealBonddia,2);

        double remanenteBonddia = CommonsUtil.round((remanente - montoRealBonddia),7);

        double intBrutosBonddia = CommonsUtil.round((tasaBonddia*100/36000)*plazo*montoRealBonddia,7);
        arreglo[4] = CommonsUtil.round(intBrutosBonddia,2);

        double tInvertido = CommonsUtil.round((montoRealCetes+montoRealBonddia),2);
        double tRendimiento = CommonsUtil.round((intBrutosCetes+intBrutosBonddia),2);

        arreglo[5] = tInvertido;
        arreglo[6] = tRendimiento;
        arreglo[7] = CommonsUtil.round((monto - tInvertido),2);

        double ISR = CommonsUtil.round((.58*tRendimiento),2);

        arreglo[8] = ISR;

        arreglo[9] = CommonsUtil.round((tInvertido+tRendimiento-ISR),2);

        // Tasas del archivo de propiedades
        arreglo[10] = CommonsUtil.round(tasaCetes*100,2);
        arreglo[11] = CommonsUtil.round(tasaBonddia*100,2);

        arreglo[12] = iPart;
        arreglo[13] = tBondia;
        arreglo[14] = plazo;

        double xInteresBruto = CommonsUtil.round((arreglo[2]  + arreglo[4])/100,7);

        cetes.setNoTitulosCetes(arreglo[12]);
        cetes.setTasaBruta(arreglo[10]);
        cetes.setInversion(arreglo[1]);
        cetes.setNoTitulosBonddia(arreglo[13]);
        cetes.setTasaBrutaBonddia(arreglo[11]);
        cetes.setInversionBonddia(arreglo[3]);
        cetes.setInteresBruto(xInteresBruto);
        cetes.setIsr(arreglo[8]);
        cetes.setMontoTotal(arreglo[9]);

        return formatCetes(cetes);
    }

    /**
     * Dar formato al objeto Cetes
     * */
    private CetesDto formatCetes(Cetes cetes){
        CetesDto cetesDto = new CetesDto();
        cetesDto.setNoTitulosCetes(CommonsUtil.doubleToFormatString(cetes.getNoTitulosCetes()));
        cetesDto.setTasaBrutaCetes(CommonsUtil.doubleToFormatString(cetes.getTasaBruta()));
        cetesDto.setInversionCetes(CommonsUtil.doubleToFormatString(cetes.getInversion()));
        cetesDto.setNoTitulosBonddia(CommonsUtil.doubleToFormatString(cetes.getNoTitulosBonddia()));
        cetesDto.setTasaBrutaBonddia(CommonsUtil.doubleToFormatString(cetes.getTasaBrutaBonddia()));
        cetesDto.setInversionBonddia(CommonsUtil.doubleToFormatString(cetes.getInversionBonddia()));
        cetesDto.setInteresBruto(CommonsUtil.doubleToFormatString(cetes.getInteresBruto()));
        cetesDto.setIsr(CommonsUtil.doubleToFormatString(cetes.getIsr()));
        cetesDto.setMontoTotal(CommonsUtil.doubleToFormatString(cetes.getMontoTotal()));

        return cetesDto;
    }
}
