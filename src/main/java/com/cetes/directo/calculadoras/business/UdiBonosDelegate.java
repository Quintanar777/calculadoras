package com.cetes.directo.calculadoras.business;

import com.cetes.directo.calculadoras.beans.Bonos;
import com.cetes.directo.calculadoras.dto.BonosDto;
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
public class UdiBonosDelegate {

    public static final Logger logger = LoggerFactory.getLogger(BonosDelegate.class);

    @Value("${calc.cetes.tasa.28}")
    private double tasaCetes;
    @Value("${calc.bonddia.tasa}")
    private double tasaBonddia;
    @Value("${calc.bonddia.precio}")
    private double precioBonddia;

    @Value("${calc.udibonos_excel.tasaudibonos}")
    private double tasaBonos;
    @Value("${calc.udibonos_excel.precioudibonos}")
    private double precioBonos;
    @Value("${calc.bonos_excel.preciocetes}")
    private double precioCetes;
    @Value("${calc.bonos_excel.diasCupon}")
    private double diasCupon;
    @Value("${calc.bonos_excel.valornominalbono}")
    private double valorNominalBono;

    @Value("${calc.cetes.factor.isr}")
    private Double factorISR;


    /**
     * Realizar calculo de BONOS
     */
    public List<BonosDto> calcUdiBonos(double monto, int plazo) {
        logger.info("Service calcular cetes...");
        logger.info(("monto: " + monto));
        logger.info(("plazo: " + plazo));

        List<BonosDto> requestBonos = new ArrayList<>();
        //Realizar calculo primer mes
        Bonos bonosMesUno = getDatosBONOS(1, Constantes.PLAZO_28, monto, monto, 0);
        requestBonos.add(formatBonos(bonosMesUno));

        double montoEnviado = bonosMesUno.getTotal();
        double montoCalculado = bonosMesUno.getTotalUltimo();
        double reinversion_cc = bonosMesUno.getReinversionCC();

        for (int i = 2; i <= plazo; i++) {
            Bonos calculoBonosInicial = getDatosBONOS(i, Constantes.PLAZO_28, monto, montoEnviado, reinversion_cc);
            requestBonos.add(formatBonos(calculoBonosInicial));

            montoEnviado = calculoBonosInicial.getTotal();
            montoCalculado = calculoBonosInicial.getTotalUltimo();
            reinversion_cc = calculoBonosInicial.getReinversionCC();

            if (i%6==0) monto= montoCalculado+reinversion_cc;

        }

        return requestBonos;
    }

    /**
     * formato bondes
     */
    private BonosDto formatBonos(Bonos bondes) {
        BonosDto dto = new BonosDto();
        dto.setInversion(CommonsUtil.doubleToFormatString(bondes.getInversion()));
        dto.setTasaCetes(CommonsUtil.doubleToFormatString(bondes.getTasaCetes()));
        dto.setTasaBonos(CommonsUtil.doubleToFormatString(bondes.getTasaBonos()));
        dto.setPlazo(CommonsUtil.doubleToFormatString(bondes.getPlazo()));
        dto.setRendimiento(CommonsUtil.doubleToFormatString(bondes.getRendimiento()));
        dto.setIsr(CommonsUtil.doubleToFormatString(bondes.getIsr()));
        dto.setTotal(CommonsUtil.doubleToFormatString(bondes.getTotal()));
        dto.setMontoBonos(CommonsUtil.doubleToFormatString(bondes.getMontoBonos()));
        dto.setTituloBonos(CommonsUtil.doubleToFormatString_NoDecimals(bondes.getTituloBonos()));
        dto.setCorteCupon(CommonsUtil.doubleToFormatString(bondes.getCorteCupon()));
        dto.setImpuestoCorteCupon(CommonsUtil.doubleToFormatString(bondes.getImpuestoCorteCupon()));
        dto.setRemanenteCetes(CommonsUtil.doubleToFormatString(bondes.getRemanenteCetes()));
        dto.setMontoCetes(CommonsUtil.doubleToFormatString(bondes.getMontoCetes()));
        dto.setTitulosCetes(CommonsUtil.doubleToFormatString_NoDecimals(bondes.getTitulosCetes()));
        dto.setRendimientoCetes(CommonsUtil.doubleToFormatString(bondes.getRendimientoCetes()));
        dto.setImpuestoCetes(CommonsUtil.doubleToFormatString(bondes.getImpuestoCetes()));
        dto.setRemanenteBonddia(CommonsUtil.doubleToFormatString(bondes.getRemanenteBonddia()));
        dto.setMontoBonddia(CommonsUtil.doubleToFormatString(bondes.getMontoBonddia()));
        dto.setRendimientoBonddia(CommonsUtil.doubleToFormatString(bondes.getRendimientoBonddia()));
        dto.setRemanentes(CommonsUtil.doubleToFormatString(bondes.getRemanentes()));
        dto.setTotalUltimo(CommonsUtil.doubleToFormatString(bondes.getTotalUltimo()));
        dto.setInteresBruto(CommonsUtil.doubleToFormatString(bondes.getInteresBruto()));
        dto.setReinversionCC(CommonsUtil.doubleToFormatString(bondes.getReinversionCC()));

        return dto;
    }

    /**
     * momento: bandera para el primer calculo
     * dias: 28 constante
     * montoInvertirInicial: el primer monto
     * montoInvertir: monto calculado de la primera vez y anterior
     * reinversionCC:
     */
    public Bonos getDatosBONOS(int momento, int dias, double montoInvertirInicial, double montoInvertir, double reinversionCC) {
        Bonos bonos = new Bonos();
        //Calculos

        double rendimiento = CommonsUtil.round((montoInvertir * (tasaCetes / 100)) / 360 * dias, 2);
        double isr = CommonsUtil.round((montoInvertir * factorISR) / 36500 * dias, 2);
        double total = CommonsUtil.round(montoInvertir + rendimiento - isr, 2);

        // Multiplo inferior monto para bondes

        double monto_para_bonos = CommonsUtil.round((montoInvertirInicial - (montoInvertirInicial % precioBonos)), 2);

        double division_titulos_bondes = monto_para_bonos / precioBonos;
        int titulos_bondes = (int) division_titulos_bondes;

        double corte_cupon = ((((tasaBonos / 100) * diasCupon) / 360) * valorNominalBono) * titulos_bondes;
        corte_cupon = CommonsUtil.round(corte_cupon, 2);

        double impuesto_corte_cupon = (titulos_bondes * precioBonos * factorISR) / 36500 * diasCupon;
        impuesto_corte_cupon = CommonsUtil.round(impuesto_corte_cupon, 2);

        double remanente_para_cetes = 0;
        if (momento == 1) {
            remanente_para_cetes = CommonsUtil.round(montoInvertirInicial - monto_para_bonos, 2);
        } else {
            if (momento % 7 == 0) {
                remanente_para_cetes = CommonsUtil.round(montoInvertir - monto_para_bonos + reinversionCC, 2);
            } else {
                remanente_para_cetes = CommonsUtil.round(reinversionCC - (reinversionCC % precioCetes), 2);
            }
        }

        // Multiplo inferior monto para cetes

        double monto_para_cetes = CommonsUtil.round(remanente_para_cetes - (remanente_para_cetes % precioCetes), 2);


        double division_titulos_cetes = monto_para_cetes / precioCetes;
        int titulos_cetes = (int) division_titulos_cetes;

        double rendimiento_cetes = CommonsUtil.round(((monto_para_cetes * (tasaCetes / 100)) / 360) * dias, 2);
        double impuesto_cetes = CommonsUtil.round((titulos_cetes * 10 * factorISR) / 36500 * dias, 2);
        double remanente_para_bonddia = 0;
        if (momento == 1) {
            remanente_para_bonddia = CommonsUtil.round(montoInvertir - monto_para_bonos - monto_para_cetes, 2);
        } else {
            remanente_para_bonddia = CommonsUtil.round(reinversionCC - (reinversionCC - (reinversionCC % precioCetes)), 2);
        }

        // Multiplo inferior monto para bonddia
        double monto_para_bonddia = CommonsUtil.round(remanente_para_bonddia - (remanente_para_bonddia % precioBonddia), 2);

        double rendimiento_bonddia = CommonsUtil.round((monto_para_bonddia * (tasaBonddia / 100)) / 360 * dias, 2);

        double remanentes = CommonsUtil.round(remanente_para_bonddia - monto_para_bonddia, 2);


        double total_total = monto_para_cetes + rendimiento_cetes + monto_para_bonddia + rendimiento_bonddia;
        total_total += remanentes + monto_para_bonos + corte_cupon - impuesto_corte_cupon - impuesto_cetes;

        total_total = CommonsUtil.round(total_total, 2);

        double reinversion_cc = monto_para_cetes + rendimiento_cetes + monto_para_bonddia + rendimiento_bonddia;
        reinversion_cc += remanentes - impuesto_cetes;

        reinversion_cc = CommonsUtil.round(reinversion_cc, 2);

        //Genera arreglo
        bonos.setInversion(montoInvertir);
        bonos.setTasaCetes(tasaCetes);
        bonos.setTasaBonos(tasaBonos);
        bonos.setPlazo(dias);
        bonos.setRendimiento(rendimiento);
        bonos.setIsr(isr);
        bonos.setTotal(total);
        bonos.setMontoBonos(monto_para_bonos);
        bonos.setTituloBonos(titulos_bondes);
        bonos.setCorteCupon(corte_cupon);
        bonos.setImpuestoCorteCupon(impuesto_corte_cupon);
        bonos.setRemanenteCetes(remanente_para_cetes);
        bonos.setMontoCetes(monto_para_cetes);
        bonos.setTitulosCetes(titulos_cetes);
        bonos.setRendimientoCetes(rendimiento_cetes);
        bonos.setImpuestoCetes(impuesto_cetes);
        bonos.setRemanenteBonddia(remanente_para_bonddia);
        bonos.setMontoBonddia(monto_para_bonddia);
        bonos.setRendimientoBonddia(rendimiento_bonddia);
        bonos.setRemanentes(remanentes);
        bonos.setTotalUltimo(total_total);
        bonos.setReinversionCC(reinversion_cc);

        return bonos;
    }
}
