package com.cetes.directo.calculadoras.controller;

import com.cetes.directo.calculadoras.business.UdiBonosDelegate;
import com.cetes.directo.calculadoras.dto.BonosDto;
import com.cetes.directo.calculadoras.request.RequestCommonsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UdibonosController {

    public static final Logger logger = LoggerFactory.getLogger(UdibonosController.class);
    @Autowired
    private UdiBonosDelegate udiBonosDelegate;

    /**
     * vista principal de cetes
     * */
    @RequestMapping("udibonos")
    public String cetes(){
        return "udibonos/main-udibonos";
    }

    /**
     * Calcular bondes
     * */
    @RequestMapping("calc/udibonos")
    public ResponseEntity<?> calcBondes(@RequestBody RequestCommonsDTO request){
        logger.info("calculando udibonos..");
        logger.debug("monto: " + request.getMonto());
        logger.debug("plazo: " + request.getPlazo());

        List<BonosDto> calcBonosResponse = udiBonosDelegate.calcUdiBonos(request.getMonto(), request.getPlazo());

        StringBuilder tabla = new StringBuilder("<table class='table table-bordered tableCetes'>");

        //Se crea encabezado
        tabla.append("<thead>");
        tabla.append("<tr>");
        tabla.append("<th></th>");
        //obtener el numero de semestres
        int numSem = request.getPlazo() /6;
        for(int i=1; i<= numSem; i++){
            tabla.append("<th>Semestre ").append(i).append("</th>");
        }

        tabla.append("</tr>");
        tabla.append("</thead>");

        tabla.append("<tbody>");

        //Se crea el contenido
        tabla.append("<tr>");
        tabla.append("<td>Monto Udibonos</td>");
        int auxIndex = 5;
        while(auxIndex<= calcBonosResponse.size()){
            BonosDto bondes = calcBonosResponse.get(auxIndex);
            auxIndex += 6;
            tabla.append("<td>");
            tabla.append(bondes.getMontoBonos());
            tabla.append("</td>");
        }
        tabla.append("</tr>");

        tabla.append("<tr>");
        tabla.append("<td>Monto Cetes</td>");
        auxIndex = 5;
        while(auxIndex<= calcBonosResponse.size()){
            BonosDto bondes = calcBonosResponse.get(auxIndex);
            auxIndex += 6;
            tabla.append("<td>");
            tabla.append(bondes.getMontoCetes());
            tabla.append("</td>");
        }
        tabla.append("</tr>");

        tabla.append("<tr>");
        tabla.append("<td>Monto Bonddia</td>");
        auxIndex = 5;
        while(auxIndex<= calcBonosResponse.size()){
            BonosDto bondes = calcBonosResponse.get(auxIndex);
            auxIndex += 6;
            tabla.append("<td>");
            tabla.append(bondes.getMontoBonddia());
            tabla.append("</td>");
        }
        tabla.append("</tr>");

        tabla.append("<tr>");
        tabla.append("<td>Corte cupon</td>");
        auxIndex = 5;
        while(auxIndex<= calcBonosResponse.size()){
            BonosDto bondes = calcBonosResponse.get(auxIndex);
            auxIndex += 6;
            tabla.append("<td>");
            tabla.append(bondes.getCorteCupon());
            tabla.append("</td>");
        }
        tabla.append("</tr>");


        tabla.append("<tr>");
        tabla.append("<td>Rendimiento Udibonos</td>");
        auxIndex = 5;
        while(auxIndex<= calcBonosResponse.size()){
            BonosDto bondes = calcBonosResponse.get(auxIndex);
            auxIndex += 6;
            tabla.append("<td>");
            tabla.append(bondes.getRendimiento());
            tabla.append("</td>");
        }
        tabla.append("</tr>");

        tabla.append("<tr>");
        tabla.append("<td>Rendimiento Cetes</td>");
        auxIndex = 5;
        while(auxIndex<= calcBonosResponse.size()){
            BonosDto bondes = calcBonosResponse.get(auxIndex);
            auxIndex += 6;
            tabla.append("<td>");
            tabla.append(bondes.getRendimientoCetes());
            tabla.append("</td>");
        }
        tabla.append("</tr>");

        tabla.append("<tr>");
        tabla.append("<td>Rendimiento Bonddia</td>");
        auxIndex = 5;
        while(auxIndex<= calcBonosResponse.size()){
            BonosDto bondes = calcBonosResponse.get(auxIndex);
            auxIndex += 6;
            tabla.append("<td>");
            tabla.append(bondes.getRendimientoBonddia());
            tabla.append("</td>");
        }
        tabla.append("</tr>");

        tabla.append("<tr>");
        tabla.append("<td>Remanentes</td>");
        auxIndex = 5;
        while(auxIndex<= calcBonosResponse.size()){
            BonosDto bondes = calcBonosResponse.get(auxIndex);
            auxIndex += 6;
            tabla.append("<td>");
            tabla.append(bondes.getRemanentes());
            tabla.append("</td>");
        }
        tabla.append("</tr>");

        tabla.append("<tr>");
        tabla.append("<td>Reinversión CC</td>");
        auxIndex = 5;
        while(auxIndex<= calcBonosResponse.size()){
            BonosDto bondes = calcBonosResponse.get(auxIndex);
            auxIndex += 6;
            tabla.append("<td>");
            tabla.append(bondes.getReinversionCC());
            tabla.append("</td>");
        }
        tabla.append("</tr>");

        tabla.append("</tbody>");
        tabla.append("</table>");
        Map<String, Object> response = new HashMap<>();
        response.put("tabla", tabla);
        response.put("result", calcBonosResponse);

        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }
}
