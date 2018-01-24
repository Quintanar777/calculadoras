package com.cetes.directo.calculadoras.controller;

import com.cetes.directo.calculadoras.business.CetesDelegate;
import com.cetes.directo.calculadoras.dto.CetesDto;
import com.cetes.directo.calculadoras.request.RequestCommonsDTO;
import com.cetes.directo.calculadoras.request.RequestReInvertir;
import com.cetes.directo.calculadoras.util.Constantes;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        CetesDto cetes = cetesDelegate.calcCetes(1,request.getMonto(), request.getPlazo());
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
                CetesDto cetes = cetesDelegate.calcCetes(1,request.getMonto(), request.getPlazo());
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                requestCetes.add(cetes);
            }else{
                CetesDto cetes = cetesDelegate.calcCetes(i,montoReinvertir, Constantes.PLAZO_28);
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                requestCetes.add(cetes);
            }

        }

        return new ResponseEntity<List<CetesDto>>(requestCetes, HttpStatus.OK);
    }

    /**
     * Comparar cetes
     * */
    @RequestMapping("calc/cetes/comparar")
    public ResponseEntity<?> compararCetes(@RequestBody RequestCommonsDTO request){
        logger.info("comparando cetes..");
        logger.debug("monto: " + request.getMonto());

        Map<String, Object> reponse = new HashMap<>();
        reponse.put("comp28",cetesDelegate.calcCetes(1,request.getMonto(), Constantes.PLAZO_28));
        reponse.put("comp91",cetesDelegate.calcCetes(1,request.getMonto(), Constantes.PLAZO_91));
        reponse.put("comp182",cetesDelegate.calcCetes(1,request.getMonto(), Constantes.PLAZO_182));
        reponse.put("comp360",cetesDelegate.calcCetes(1,request.getMonto(), Constantes.PLAZO_360));

        return new ResponseEntity<Map>(reponse, HttpStatus.OK);
    }

    /**
     * Comparar cetes
     * */
    @RequestMapping("calc/cetes/comparar/reinvertir")
    public ResponseEntity<?> compararCetesReinvertir(@RequestBody RequestReInvertir request){
        logger.info("calculando cetes..");
        logger.debug("monto: " + request.getMonto());
        logger.debug("plazo: " + request.getPlazo());
        logger.debug("periodos: " + request.getPeriodos());
        List<CetesDto> responseCetes28 = new ArrayList<>();
        List<CetesDto> responseCetes91 = new ArrayList<>();
        List<CetesDto> responseCetes182 = new ArrayList<>();
        List<CetesDto> responseCetes360 = new ArrayList<>();

        Map<String, Object> response = new HashMap<>();
        double montoReinvertir = 0;

        //Reeinvertir a 28
        for(int i=1; i<=request.getPeriodos(); i++){
            if(i==1){
                CetesDto cetes = cetesDelegate.calcCetes(1,request.getMonto(), Constantes.PLAZO_28);
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                responseCetes28.add(cetes);
            }else{
                CetesDto cetes = cetesDelegate.calcCetes(i,montoReinvertir, Constantes.PLAZO_28);
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                responseCetes28.add(cetes);
            }

        }
        response.put("responseCetes28", responseCetes28);

        //Reeinvertir a 91
        for(int i=1; i<=request.getPeriodos(); i++){
            if(i==1){
                CetesDto cetes = cetesDelegate.calcCetes(1,request.getMonto(), Constantes.PLAZO_91);
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                responseCetes91.add(cetes);
            }else{
                CetesDto cetes = cetesDelegate.calcCetes(i,montoReinvertir, Constantes.PLAZO_28);
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                responseCetes91.add(cetes);
            }

        }
        response.put("responseCetes91", responseCetes91);

        //Reeinvertir a 182
        for(int i=1; i<=request.getPeriodos(); i++){
            if(i==1){
                CetesDto cetes = cetesDelegate.calcCetes(1,request.getMonto(), Constantes.PLAZO_182);
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                responseCetes182.add(cetes);
            }else{
                CetesDto cetes = cetesDelegate.calcCetes(i,montoReinvertir, Constantes.PLAZO_28);
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                responseCetes182.add(cetes);
            }

        }
        response.put("responseCetes182", responseCetes182);

        //Reeinvertir a 360
        for(int i=1; i<=request.getPeriodos(); i++){
            if(i==1){
                CetesDto cetes = cetesDelegate.calcCetes(1,request.getMonto(), Constantes.PLAZO_360);
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                responseCetes360.add(cetes);
            }else{
                CetesDto cetes = cetesDelegate.calcCetes(i,montoReinvertir, Constantes.PLAZO_28);
                montoReinvertir = Double.parseDouble(cetes.getMontoTotal().replace(",", ""));
                responseCetes360.add(cetes);
            }

        }
        response.put("responseCetes360", responseCetes360);

        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }

}
