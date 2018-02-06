package com.cetes.directo.calculadoras.business;

import com.cetes.directo.calculadoras.beans.Bondes;
import com.cetes.directo.calculadoras.dto.BondesDto;
import com.cetes.directo.calculadoras.util.CommonsUtil;
import com.cetes.directo.calculadoras.util.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BondesDelegate {

    public static final Logger logger = LoggerFactory.getLogger(BondesDelegate.class);

    @Value("${calc.bondes_excel.tasabondes}")
    private double tasaBondes;
    @Value("${calc.bondes_excel.preciobondes}")
    private double precioBondes;
    @Value("${calc.bondes_excel.tasacetes}")
    private double tasaCetes;
    @Value("${calc.bondes_excel.preciocetes}")
    private double precioCetes;
    @Value("${calc.bondes_excel.tasabonddia}")
    private double tasaBonddia;
    @Value("${calc.bondes_excel.preciobonddia}")
    private double precioBonddia;
    @Value("${calc.bondes_excel.diasCupon}")
    private double diasCupon;
    @Value("${calc.bondes_excel.valornominalbono}")
    private double valorNominalBono;

    @Autowired
    private Bondes bondes;

    /**
     * Calcular bondes
     */
    public List calcBondes(double monto, int plazo) {
        logger.info("Service calcular cetes...");
        logger.info(("monto: " + monto));
        logger.info(("plazo: " + plazo));

        List<BondesDto> requestBondes = new ArrayList<>();

        //Realizar calculo primer mes
        Bondes bondesMesUno = getDatosBONDES(1, Constantes.PLAZO_28, monto, monto, 0);
        requestBondes.add(formatBondes(bondesMesUno));

        for (int i = 2; i <= plazo; i++) {
            bondesMesUno = getDatosBONDES(i, Constantes.PLAZO_28, monto, bondesMesUno.getTotal(), bondesMesUno.getTotalUltimo());
            requestBondes.add(formatBondes(bondesMesUno));
        }

        return requestBondes;
    }


    /**
     * formato bondes
     */
    private BondesDto formatBondes(Bondes bondes) {
        BondesDto dto = new BondesDto();
        dto.setInversion(CommonsUtil.doubleToFormatString(bondes.getInversion()));
        dto.setTasaCetes(CommonsUtil.doubleToFormatString(bondes.getTasaCetes()));
        dto.setTasaBondes(CommonsUtil.doubleToFormatString(bondes.getTasaBondes()));
        dto.setPlazo(CommonsUtil.doubleToFormatString(bondes.getPlazo()));
        dto.setRendimiento(CommonsUtil.doubleToFormatString(bondes.getRendimiento()));
        dto.setIsr(CommonsUtil.doubleToFormatString(bondes.getIsr()));
        dto.setTotal(CommonsUtil.doubleToFormatString(bondes.getTotal()));
        dto.setMontoBondes(CommonsUtil.doubleToFormatString(bondes.getMontoBondes()));
        dto.setTituloBondes(CommonsUtil.doubleToFormatString(bondes.getTituloBondes()));
        dto.setCorteCupon(CommonsUtil.doubleToFormatString(bondes.getCorteCupon()));
        dto.setImpuestoCorteCupon(CommonsUtil.doubleToFormatString(bondes.getImpuestoCorteCupon()));
        dto.setRemanenteCetes(CommonsUtil.doubleToFormatString(bondes.getRemanenteCetes()));
        dto.setMontoCetes(CommonsUtil.doubleToFormatString(bondes.getMontoCetes()));
        dto.setTitulosCetes(CommonsUtil.doubleToFormatString(bondes.getTitulosCetes()));
        dto.setRendimientoCetes(CommonsUtil.doubleToFormatString(bondes.getRendimientoCetes()));
        dto.setImpuestoCetes(CommonsUtil.doubleToFormatString(bondes.getImpuestoCetes()));
        dto.setRemanenteBonddia(CommonsUtil.doubleToFormatString(bondes.getRemanenteBonddia()));
        dto.setMontoBonddia(CommonsUtil.doubleToFormatString(bondes.getMontoBonddia()));
        dto.setRendimientoBonddia(CommonsUtil.doubleToFormatString(bondes.getRendimientoBonddia()));
        dto.setRemanentes(CommonsUtil.doubleToFormatString(bondes.getRemanentes()));
        dto.setTotalUltimo(CommonsUtil.doubleToFormatString(bondes.getTotalUltimo()));
        dto.setInteresBruto(CommonsUtil.doubleToFormatString(bondes.getInteresBruto()));

        return dto;
    }

    /**
     * momento: bandera para el primer calculo
     * dias: 28 constante
     * montoInvertirInicial: el primer monto
     * montoInvertir: monto calculado de la primera vez y anterior
     * totalCalculado: calculado de la primera vez y anterior
     */
    public Bondes getDatosBONDES(int primerMes, int dias, double montoInvertirInicial, double montoInvertir, double totalCalculado) {
        logger.info("primerMes: " + primerMes);
        logger.info("dias: " + dias);
        logger.info("montoInvertirInicial: " + montoInvertirInicial);
        logger.info("montoInvertir: " + montoInvertir);
        logger.info("totalCalculado: " + totalCalculado);
        logger.info("\n");


        //Calculos
        
        montoInvertir = CommonsUtil.round(montoInvertir, 2);

        double rendimiento = (montoInvertir*(tasaCetes/100))/360*dias;
	    rendimiento = CommonsUtil.round(rendimiento, 2);
	    
	    double isr = (montoInvertir*.58)/36500*dias;
	    isr = CommonsUtil.round(isr, 2);
	    
	    double total = montoInvertir+rendimiento-isr;
	    total = CommonsUtil.round(total, 2);
	    
	    // Multiplo inferior monto para bondes
	    double monto_para_bonos = montoInvertirInicial -(montoInvertirInicial%precioBondes);
//	    monto_para_bonos = CommonsUtil.round(monto_para_bonos);
	    
	    double division_titulos_bondes = monto_para_bonos/precioBondes;
	    int titulos_bondes = (int) division_titulos_bondes;
	    
	    double corte_cupon = (((((tasaBondes/100)/100)*dias)/360)*valorNominalBono)*monto_para_bonos;
	    corte_cupon = Math.ceil(corte_cupon);
	    
	    double impuesto_corte_cupon = (titulos_bondes*.58)/36500*diasCupon;
	    impuesto_corte_cupon = CommonsUtil.round(impuesto_corte_cupon, 2);
        
	    double remanente_para_cetes = 0;
	    if (primerMes==1)  {
	    	remanente_para_cetes = montoInvertirInicial - monto_para_bonos;
	    } else {
	    	remanente_para_cetes = totalCalculado - monto_para_bonos;
	    }
	    remanente_para_cetes = CommonsUtil.round(remanente_para_cetes, 2);

	    // Multiplo inferior monto para cetes
	    double monto_para_cetes = remanente_para_cetes -(remanente_para_cetes%precioCetes);
	    monto_para_cetes = CommonsUtil.round(monto_para_cetes, 2);

	    double division_titulos_cetes = monto_para_cetes/precioCetes;
	    int titulos_cetes = (int) division_titulos_cetes;
	    
	    double rendimiento_cetes = ((monto_para_cetes*(tasaCetes/100))/360)*diasCupon;
	    rendimiento_cetes = CommonsUtil.round(rendimiento_cetes, 2);
	    
	    double impuesto_cetes = (titulos_cetes*.58)/36500*diasCupon;
	    impuesto_cetes = CommonsUtil.round(impuesto_cetes, 3);
        
        
    double remanente_para_bonddia = 0;
	    
	    if (primerMes ==1) { 	remanente_para_bonddia = montoInvertir-monto_para_bonos-monto_para_cetes; }
    	// Preguntar de acuerdo al excel
	   	if (primerMes==2) { 	remanente_para_bonddia = totalCalculado-monto_para_bonos-monto_para_cetes; }
	    if (primerMes>2) {
	    		remanente_para_bonddia = totalCalculado -(totalCalculado%precioCetes);
	    		remanente_para_bonddia = totalCalculado - remanente_para_bonddia;
	    }	
        remanente_para_bonddia=CommonsUtil.round(remanente_para_bonddia, 2);

	    // Multiplo inferior monto para bonddia
	    double monto_para_bonddia = remanente_para_bonddia -(remanente_para_bonddia%precioBonddia);
//	    monto_para_bonddia = calculo.round(monto_para_bonddia, 2);
	    
	    double rendimiento_bonddia = (monto_para_bonddia*(tasaBonddia/100))/360*dias;
	    rendimiento_bonddia = CommonsUtil.round(rendimiento_bonddia, 3);

	    double remanentes=0;
	    if (primerMes == 1) {
	    	remanentes = montoInvertir - monto_para_bonos - monto_para_cetes - monto_para_bonddia;
	    } else {
	    	double MI_1 = totalCalculado - (totalCalculado%precioCetes);
	    	double resta = totalCalculado - MI_1;
	    	double MI_2 = resta - (resta%precioBonddia);
	    	remanentes = totalCalculado - MI_1 - MI_2;
	    }
	    remanentes = CommonsUtil.round(remanentes, 2);

	    double total_total = monto_para_cetes+rendimiento_cetes+monto_para_bonddia+rendimiento_bonddia;
	    total_total += remanentes + monto_para_bonos + corte_cupon - impuesto_corte_cupon - impuesto_cetes;
	    
	    total_total = CommonsUtil.round(total_total, 2);

        //Genera arreglo
        bondes.setInversion(montoInvertir);
        bondes.setTasaCetes(tasaCetes);
        bondes.setTasaBondes(tasaBondes);
        bondes.setPlazo(dias);
        bondes.setRendimiento(rendimiento + rendimiento_cetes);
        bondes.setIsr(isr + impuesto_cetes);
        bondes.setTotal(total);
        bondes.setMontoBondes(monto_para_bonos);
        bondes.setTituloBondes(titulos_bondes);
        bondes.setCorteCupon(corte_cupon);
        bondes.setImpuestoCorteCupon(impuesto_corte_cupon);
        bondes.setRemanenteCetes(remanente_para_cetes);
        bondes.setMontoCetes(monto_para_cetes);
        bondes.setTitulosCetes(titulos_cetes);
        bondes.setRendimientoCetes(rendimiento_cetes);
        bondes.setImpuestoCetes(impuesto_cetes);
        bondes.setRemanenteBonddia(remanente_para_bonddia);
        bondes.setMontoBonddia(monto_para_bonddia);
        bondes.setRendimientoBonddia(rendimiento_bonddia);
        bondes.setRemanentes(remanentes);
        bondes.setTotalUltimo(total_total);

        return bondes;

    }
}
