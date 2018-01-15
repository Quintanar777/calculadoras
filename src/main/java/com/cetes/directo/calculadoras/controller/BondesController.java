package com.cetes.directo.calculadoras.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BondesController {
    /**
     * vista principal de cetes
     * */
    @RequestMapping("bondes")
    public String cetes(){
        return "bondes/main-bondes";
    }
}
