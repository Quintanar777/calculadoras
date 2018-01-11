package com.cetes.directo.calculadoras.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CetesController {

    /**
     * vista principal de cetes
     * */
    @RequestMapping("cetes")
    public String cetes(){
        return "cetes/main-cetes";
    }
}
