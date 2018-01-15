package com.cetes.directo.calculadoras.business;

import com.cetes.directo.calculadoras.dto.BondesDto;
import com.cetes.directo.calculadoras.util.CommonsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    /**
     * Calcular bondes
     * */
    public BondesDto calcBondes(double monto, int plazo){
        logger.info("Service calcular cetes...");
        logger.info(("monto: " + monto));
        logger.info(("plazo: " + plazo));

        //mandar llamar metodo de bondes Jorge

        return new BondesDto();
    }

    public String[][] getDatosBONDES(int momento, int dias, double montoInvertirInicial,double montoInvertir, double totalCalculado) {

        String[][] arregloBONDES_titulos = new String [21][2];

        //Calculos

        double rendimiento = (montoInvertir*(tasaCetes/100))/360*dias;
        double isr = (montoInvertir*.58)/36500*dias;
        double total = montoInvertir+rendimiento-isr;

        // Multiplo inferior monto para bondes
        double monto_para_bonos = montoInvertirInicial -(montoInvertirInicial%precioBondes);

        double division_titulos_bondes = monto_para_bonos/precioBondes;
        int titulos_bondes = (int) division_titulos_bondes;

        double corte_cupon = (((((tasaBondes/100)/100)*diasCupon)/360)*valorNominalBono)*monto_para_bonos;
        double impuesto_corte_cupon = (titulos_bondes*.58)/36500*diasCupon;

        double remanente_para_cetes = 0;
        if (momento==1)  {
            remanente_para_cetes = montoInvertirInicial - monto_para_bonos;
        } else {
            remanente_para_cetes = totalCalculado - monto_para_bonos;
        }

        // Multiplo inferior monto para cetes
        double monto_para_cetes = remanente_para_cetes -(remanente_para_cetes%precioCetes);

        double division_titulos_cetes = monto_para_cetes/precioCetes;
        int titulos_cetes = (int) division_titulos_cetes;

        double rendimiento_cetes = ((monto_para_cetes*(tasaCetes/100))/360)*dias;
        double impuesto_cetes = (titulos_cetes*.58)/36500*dias;
        double remanente_para_bonddia = 0;
        if (momento ==1) {
            remanente_para_bonddia = montoInvertir-monto_para_bonos-monto_para_cetes;
        } else {
            // Preguntar de acuerdo al excel

            //remanente_para_bonddia = totalCalculado-monto_para_bonos-monto_para_cetes;

            remanente_para_bonddia = totalCalculado -(totalCalculado%precioCetes);
            remanente_para_bonddia = totalCalculado - remanente_para_bonddia;
        }

        // Multiplo inferior monto para bonddia
        double monto_para_bonddia = remanente_para_bonddia -(remanente_para_bonddia%precioBonddia);

        double rendimiento_bonddia = (monto_para_bonddia*(tasaBonddia/100))/360*dias;

        double remanentes=0;
        if (momento == 1) {
            remanentes = montoInvertir - monto_para_bonos - monto_para_cetes - monto_para_bonddia;
        } else {
            double MI_1 = totalCalculado - (totalCalculado%precioCetes);
            double resta = totalCalculado - MI_1;
            double MI_2 = resta - (resta%precioBonddia);
            remanentes = totalCalculado - MI_1 - MI_2;
        }

        double total_total = monto_para_cetes+rendimiento_cetes+monto_para_bonddia+rendimiento_bonddia;
        total_total += monto_para_bonos + corte_cupon - impuesto_corte_cupon - impuesto_cetes;

        double intBrutosBondes = CommonsUtil.round((tasaBondes/36000)*dias*monto_para_bonos,7);


        //Genera arreglo

        arregloBONDES_titulos[0][0] = "Inversion";
        arregloBONDES_titulos[0][1] = String.valueOf(montoInvertir);

        arregloBONDES_titulos[1][0] = "Tasa Cetes";
        arregloBONDES_titulos[1][1] = String.valueOf(tasaCetes);

        arregloBONDES_titulos[2][0] = "Plazo";
        arregloBONDES_titulos[2][1] = String.valueOf(dias);

        arregloBONDES_titulos[3][0] = "Rendimiento";
        arregloBONDES_titulos[3][1] = String.valueOf(rendimiento);

        arregloBONDES_titulos[4][0] = "ISR";
        arregloBONDES_titulos[4][1] = String.valueOf(isr);

        arregloBONDES_titulos[5][0] = "Total";
        arregloBONDES_titulos[5][1] = String.valueOf(total);

        arregloBONDES_titulos[6][0] = "Monto para Bondes";
        arregloBONDES_titulos[6][1] = String.valueOf(monto_para_bonos);

        arregloBONDES_titulos[7][0] = "Titulos Bondes";
        arregloBONDES_titulos[7][1] = String.valueOf(titulos_bondes);

        arregloBONDES_titulos[8][0] = "Corte Cupon";
        arregloBONDES_titulos[8][1] = String.valueOf(corte_cupon);

        arregloBONDES_titulos[9][0] = "Impuesto Corte Cupon";
        arregloBONDES_titulos[9][1] = String.valueOf(impuesto_corte_cupon);

        arregloBONDES_titulos[10][0] = "Remanente para Cetes";
        arregloBONDES_titulos[10][1] = String.valueOf(remanente_para_cetes);

        arregloBONDES_titulos[11][0] = "Monto para Cetes";
        arregloBONDES_titulos[11][1] = String.valueOf(monto_para_cetes);

        arregloBONDES_titulos[12][0] = "Titulos Cetes";
        arregloBONDES_titulos[12][1] = String.valueOf(titulos_cetes);

        arregloBONDES_titulos[13][0] = "Rendimiento Cetes";
        arregloBONDES_titulos[13][1] = String.valueOf(rendimiento_cetes);

        arregloBONDES_titulos[14][0] = "Impuesto Cetes";
        arregloBONDES_titulos[14][1] = String.valueOf(impuesto_cetes);

        arregloBONDES_titulos[15][0] = "Remanente para Bonddia";
        arregloBONDES_titulos[15][1] = String.valueOf(remanente_para_bonddia);

        arregloBONDES_titulos[16][0] = "Monto para Bonddia";
        arregloBONDES_titulos[16][1] = String.valueOf(monto_para_bonddia);

        arregloBONDES_titulos[17][0] = "Rendimiento Bonddia";
        arregloBONDES_titulos[17][1] = String.valueOf(rendimiento_bonddia);

        arregloBONDES_titulos[18][0] = "Remanentes";
        arregloBONDES_titulos[18][1] = String.valueOf(remanentes);

        arregloBONDES_titulos[19][0] = "Total Ultimo";
        arregloBONDES_titulos[19][1] = String.valueOf(total_total);

        arregloBONDES_titulos[20][0] = "Interes Bruto";
        arregloBONDES_titulos[20][1] = String.valueOf(intBrutosBondes);




        return arregloBONDES_titulos;

    }
}
