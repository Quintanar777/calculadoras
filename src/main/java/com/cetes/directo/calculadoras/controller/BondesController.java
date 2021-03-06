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
            tabla.append("<td>Inversion</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getInversion());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tbody>");
            //Se crea el contenido
            tabla.append("<tr>");
            tabla.append("<td>Rendimiento</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getRendimiento());
                tabla.append("</td>");
            }
            tabla.append("</tr>");
            
            tabla.append("<tr>");
            tabla.append("<td>ISR</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getIsr());
                tabla.append("</td>");
            }
            tabla.append("</tr>");
            
            tabla.append("<tr>");
            tabla.append("<td>Total</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getTotal());
                tabla.append("</td>");
            }
            tabla.append("</tr>");
            
            tabla.append("<tr>");
            tabla.append("<td>Remanente p/Cetes</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getRemanenteCetes());
                tabla.append("</td>");
            }
            tabla.append("</tr>");
            
            tabla.append("<tr>");
            tabla.append("<td>Monto p/Cetes</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getMontoCetes());
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
            tabla.append("<td>Impuesto Cetes</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getImpuestoCetes());
                tabla.append("</td>");
            }
            tabla.append("</tr>");
            
            tabla.append("<tr>");
            tabla.append("<td>Remanentes p/Bonddia</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getRemanenteBonddia());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Monto p/Bonddia</td>");
            for(BondesDto bondes : calcBondesResponse){
                tabla.append("<td>");
                tabla.append(bondes.getMontoBonddia());
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

  

        }else{//mostrar semestres
            //Se crea encabezado
            tabla.append("<thead>");
            tabla.append("<tr>");
            tabla.append("<th></th>");
            //obtener el numero de semestres
            int numSem = request.getPlazo() /12;
            for(int i=1; i<= numSem; i++){
                tabla.append("<th>Año ").append(i).append("</th>");
            }

            tabla.append("</tr>");
            tabla.append("</thead>");

            tabla.append("<tbody>");

            //Se crea el
            tabla.append("<tr>");
            tabla.append("<td>Inversion</td>");
            int auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getInversion());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Rendimiento</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getRendimiento());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>ISR</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getIsr());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Total</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getTotal());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Remanente p/Cetes</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getRemanenteCetes());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Monto p/Cetes</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getMontoCetes());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Rendimiento Cetes</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getRendimientoCetes());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Impuesto Cetes</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getImpuestoCetes());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Remanentes p/Bonddia</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getRemanenteBonddia());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Monto p/Bonddia</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getMontoBonddia());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Rendimiento Bonddia</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getRendimientoBonddia());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Rendimiento Bonddia</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getRendimientoBonddia());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

            tabla.append("<tr>");
            tabla.append("<td>Remanentes</td>");
            auxIndex = 11;
            while(auxIndex<= calcBondesResponse.size()){
                BondesDto bondes = calcBondesResponse.get(auxIndex);
                auxIndex += 12;
                tabla.append("<td>");
                tabla.append(bondes.getRemanentes());
                tabla.append("</td>");
            }
            tabla.append("</tr>");

        }

        tabla.append("</tbody>");
        tabla.append("</table>");
        Map<String, Object> response = new HashMap<>();
        response.put("tabla", tabla);
        response.put("result", calcBondesResponse);

        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }
}
