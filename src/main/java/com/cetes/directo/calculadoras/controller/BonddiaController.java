package com.cetes.directo.calculadoras.controller;

import com.cetes.directo.calculadoras.business.UdiBonosDelegate;
import com.cetes.directo.calculadoras.request.RequestCommonsDTO;
import com.cetes.directo.calculadoras.util.CommonsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BonddiaController {

    public static final Logger logger = LoggerFactory.getLogger(BonddiaController.class);
    @Autowired
    private UdiBonosDelegate udiBonosDelegate;
    @Value("${calc.bonddia.precio}")
    private double precioBonddia;
    @Value("${calc.bonddia.tasa}")
    private double tasaBonddia;

    /**
     * vista principal de cetes
     * */
    @RequestMapping("bonddia")
    public String cetes(){
        return "bonddia/main-bonddia";
    }

    /**
     * Calcular bonddia
     * */
    @RequestMapping("calc/bonddia")
    public ResponseEntity<?> calcBondes(@RequestBody RequestCommonsDTO request){
        logger.info("calculando udibonos..");
        logger.debug("monto: " + request.getMonto());
        logger.debug("plazo: " + request.getPlazo());
        Map<String, Object> response = new HashMap<>();

        double inversionInicial = request.getMonto() - (request.getMonto()%precioBonddia);
        double remanente = request.getMonto() - inversionInicial;
        double inversionFinal = inversionInicial + ((inversionInicial * tasaBonddia)/36000) * request.getPlazo();

        response.put("inversionInicial", CommonsUtil.doubleToFormatString(inversionInicial));
        response.put("remanente", CommonsUtil.doubleToFormatString(remanente));
        response.put("inversionFinal", CommonsUtil.doubleToFormatString(inversionFinal));
        response.put("tasa", CommonsUtil.doubleToFormatString(tasaBonddia));


        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }
}
