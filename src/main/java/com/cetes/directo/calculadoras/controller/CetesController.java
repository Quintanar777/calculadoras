package com.cetes.directo.calculadoras.controller;

import com.cetes.directo.calculadoras.business.CetesDelegate;
import com.cetes.directo.calculadoras.dto.CetesDto;
import com.cetes.directo.calculadoras.request.RequestCommonsDTO;
import com.cetes.directo.calculadoras.request.RequestReInvertir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CetesController {

    public static final Logger logger = LoggerFactory.getLogger(CetesController.class);
    @Autowired
    private CetesDelegate cetesDelegate;

    /**
     * vista principal de cetes
     * */
    @RequestMapping("cetes")
    public String cetes(){
        return "cetes/main-cetes";
    }

    /**
     * Regresa el calculo de cetes
     * */
    @RequestMapping(value = "calc/cetes", method = RequestMethod.POST)
    public ResponseEntity<?> calcCetes(@RequestBody RequestCommonsDTO request){
        logger.info("calculando cetes..");
        logger.debug("monto: " + request.getMonto());
        logger.debug("plazo: " + request.getPlazo());

        CetesDto cetes = cetesDelegate.calcCetes(request.getMonto(), request.getPlazo());
        return new ResponseEntity<CetesDto>(cetes, HttpStatus.OK);
    }

    /**
     * Obtener el arreglo del calculo de re inversión
     * */
    @RequestMapping("calc/cetes/reinvertir")
    public ResponseEntity<?> calcReinvertirCetes(@RequestBody RequestReInvertir request){
        logger.info("calculando cetes..");
        logger.debug("monto: " + request.getMonto());
        logger.debug("plazo: " + request.getPlazo());
        logger.debug("periodos: " + request.getPeriodos());
        List<CetesDto> requestCetes = new ArrayList<>();
        double montoReinvertir = 0;

        for(int i=1; i<=request.getPeriodos(); i++){
            if(i==1){
                CetesDto cetes = cetesDelegate.calcCetes(request.getMonto(), request.getPlazo());
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                requestCetes.add(cetes);
            }else{
                CetesDto cetes = cetesDelegate.calcCetes(montoReinvertir, request.getPlazo());
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                requestCetes.add(cetes);
            }

        }

        return new ResponseEntity<List<CetesDto>>(requestCetes, HttpStatus.OK);
    }
}
