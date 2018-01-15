package com.cetes.directo.calculadoras.controller;

import com.cetes.directo.calculadoras.business.BondesDelegate;
import com.cetes.directo.calculadoras.dto.BondesDto;
import com.cetes.directo.calculadoras.dto.CetesDto;
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
public class BondesController {

    public static final Logger logger = LoggerFactory.getLogger(CetesController.class);
    @Autowired
    private BondesDelegate bondesDelegate;

    /**
     * vista principal de cetes
     * */
    @RequestMapping("bondes")
    public String cetes(){
        return "bondes/main-bondes";
    }

    /**
     * Calcular bondes
     * */
    @RequestMapping("calc/bondes")
    public ResponseEntity<?> calcBondes(@RequestBody RequestCommonsDTO request){
        logger.info("calculando bondes..");
        logger.debug("monto: " + request.getMonto());
        logger.debug("plazo: " + request.getPlazo());

        List<BondesDto> calcBondesResponse = bondesDelegate.calcBondes(request.getMonto(), request.getPlazo());

        StringBuilder tabla = new StringBuilder("<table class='table table-bordered tableCetes'>");
        if(request.getPlazo() == 12){ //un año, mostrar los 12 meses
            //Se crea encabezado
            tabla.append("<thead>");
            tabla.append("<tr>");
            tabla.append("<th></th>");
            tabla.append("<th>Enero</th>");
            tabla.append("<th>Febrero</th>");
            tabla.append("<th>Marzo</th>");
            tabla.append("<th>Abril</th>");
            tabla.append("<th>Mayo</th>");
            tabla.append("<th>Junio</th>");
            tabla.append("<th>Julio</th>");
            tabla.append("<th>Agosto</th>");
            tabla.append("<th>Septiembre</th>");
            tabla.append("<th>Octubre</th>");
            tabla.append("<th>Noviembre</th>");
            tabla.append("<th>Diciembre</th>");
            tabla.append("</tr>");
            tabla.append("</thead>");

            tabla.append("<tbody>");
            //Se crea el contenido
            tabla.append("<tr>");
            tabla.append("<td>Monto Bondes</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getMontoBondes());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Monto Cetes</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getMontoCetes());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Monto Bonddia</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getMontoBonddia());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Corte cupon</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getCorteCupon());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Rendimiento Bondes</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getRendimiento());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Rendimiento Cetes</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getRendimientoCetes());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Rendimiento Bonddia</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getRendimientoBonddia());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Remanentes</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getRemanentes());
                tabla.append("</td>");
            }
            tabla.append("</tr>");


        }else{//mostrar semestres

        }

        tabla.append("</tbody>");
        tabla.append("</table>");
        Map<String, Object> response = new HashMap<>();
        response.put("tabla", tabla);
        response.put("result", calcBondesResponse);

        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }
}
