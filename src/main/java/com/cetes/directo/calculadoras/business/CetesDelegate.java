package com.cetes.directo.calculadoras.business;

import com.cetes.directo.calculadoras.beans.Cetes;
import com.cetes.directo.calculadoras.dto.CetesDto;
import com.cetes.directo.calculadoras.util.CommonsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CetesDelegate {

    public static final Logger logger = LoggerFactory.getLogger(CetesDelegate.class);

    @Value("${calc.cetes.precio}")
    private Double valorCete;

//    @Value("${calc.cetes.tasa.descuento}")
//    private Double tasaDecuento;

    @Value("${calc.cetes.factor.isr}")
	private Double factorISR;


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
    public CetesDto calcCetes(int momento, double monto, int plazo){
        logger.info("Service calcular cetes...");
        logger.info(("monto: " + monto));
        logger.info(("plazo: " + plazo));



		double arreglo[] = new double [14];

	//	double precioXcete = CommonsUtil.round(valorCete*(1-(tasaDecuento/36000*plazo)),7);
        double tasaDecuento = 0.0;
        if(plazo == 28) tasaDecuento = tasa28;
        if(plazo == 91) tasaDecuento = tasa91;
        if(plazo == 182) tasaDecuento = tasa182;
        if(plazo == 360) tasaDecuento = tasa360;

		double precioXcete = CommonsUtil.round(valorCete*(1-(tasaDecuento/36000*plazo)),7);
      logger.info(("precioXcete " + precioXcete));

		double tasaCetes = ((valorCete/precioXcete)-1)/plazo*360;
		tasaCetes = CommonsUtil.round(tasaCetes,4);

	//	tasaBonddia = CommonsUtil.round(tasaBonddia/100,7);
		tasaBonddia = tasaBonddia/100;

	    double montoRealCetes = monto -(monto%precioXcete);
      logger.info(("montoRealCetes: " + montoRealCetes));

	//	double intBrutosCetes = CommonsUtil.round(((montoRealCetes*tasaCetes)/360)*plazo,7);
		double intBrutosCetes = ((montoRealCetes*tasaCetes)/360)*plazo;

		double impuestoCetes = (montoRealCetes*factorISR)/36500*plazo;
		double remanenteBonddia = monto-montoRealCetes;
		double montoRealBonddia = remanenteBonddia -(remanenteBonddia%valorBonddia);

	//	double intBrutosBonddia = CommonsUtil.round(((montoRealBonddia*tasaBonddia)/360)*plazo,7);
		double intBrutosBonddia = ((montoRealBonddia*tasaBonddia)/360)*plazo;

		double remanente = monto-montoRealCetes-montoRealBonddia;
		double titulos = montoRealCetes/precioXcete;
		double titulosBondia = montoRealBonddia/valorBonddia;
		double total = montoRealCetes+intBrutosCetes+montoRealBonddia+intBrutosBonddia+remanente-impuestoCetes;

    logger.info(("montoRealBonddia: " + montoRealBonddia));
    logger.info(("valorBonddia: " + valorBonddia));
    logger.info(("titulosBondia: " + titulosBondia));

		double intBrutosTotal = intBrutosCetes + intBrutosBonddia;

	//	tasaCetes = CommonsUtil.round(tasaCetes*100,7);
	//    tasaBonddia = CommonsUtil.round(tasaBonddia*100,7);

	    tasaCetes = tasaCetes*100;
	    tasaBonddia = tasaBonddia*100;

        arreglo[0] = monto;
        arreglo[1] = montoRealCetes;
        arreglo[2] = intBrutosCetes;
        arreglo[3] = impuestoCetes;
        arreglo[4] = tasaCetes;
        arreglo[5] = titulos;
        arreglo[6] = montoRealBonddia;
        arreglo[7] = intBrutosBonddia;
        arreglo[8] = tasaBonddia;
		arreglo[9] = remanenteBonddia;
		arreglo[10] = titulosBondia;
		arreglo[11] = total;
		arreglo[12] = remanente;
		arreglo[13] = intBrutosTotal;

        cetes.setNoTitulosCetes(arreglo[5]);

/*
        //depende del plazo, es la tasa que se envia
        if(momento == 1){ //depende del plazo
            if(plazo == 28) cetes.setTasaBruta(tasa28);
            if(plazo == 91) cetes.setTasaBruta(tasa91);
            if(plazo == 182) cetes.setTasaBruta(tasa182);
            if(plazo == 360) cetes.setTasaBruta(tasa360);

        }else{ //de lo contrario siempre es a 28
            cetes.setTasaBruta(tasa28);
        }
*/
        cetes.setTasaBruta(arreglo[4]);

        cetes.setTasaBrutaBonddia(tasaBonddia);
        cetes.setInversion(arreglo[1]);
        cetes.setNoTitulosBonddia(arreglo[10]);
        cetes.setInversionBonddia(arreglo[6]);
        cetes.setInteresBruto(arreglo[13]);
        cetes.setIsr(arreglo[3]);
        cetes.setMontoTotal(arreglo[11]);
        cetes.setMontoInicial(arreglo[0]);

        return formatCetes(cetes);
    }

    /**
     * Dar formato al objeto Cetes
     * */
    private CetesDto formatCetes(Cetes cetes){
        CetesDto cetesDto = new CetesDto();
        cetesDto.setNoTitulosCetes(CommonsUtil.doubleToFormatString_NoDecimals(cetes.getNoTitulosCetes()));
        cetesDto.setTasaBrutaCetes(CommonsUtil.doubleToFormatString(cetes.getTasaBruta()));
        cetesDto.setInversionCetes(CommonsUtil.doubleToFormatString(cetes.getInversion()));
        cetesDto.setNoTitulosBonddia(CommonsUtil.doubleToFormatString_NoDecimals(cetes.getNoTitulosBonddia()));
        cetesDto.setTasaBrutaBonddia(CommonsUtil.doubleToFormatString(cetes.getTasaBrutaBonddia()));
        cetesDto.setInversionBonddia(CommonsUtil.doubleToFormatString(cetes.getInversionBonddia()));
        cetesDto.setInteresBruto(CommonsUtil.doubleToFormatString(cetes.getInteresBruto()));
        cetesDto.setIsr(CommonsUtil.doubleToFormatString(cetes.getIsr()));
        cetesDto.setMontoTotal(CommonsUtil.doubleToFormatString(cetes.getMontoTotal()));
        cetesDto.setMontoInicial(CommonsUtil.doubleToFormatString(cetes.getMontoInicial()));

        return cetesDto;
    }
}
