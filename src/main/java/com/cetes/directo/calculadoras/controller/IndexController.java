package com.cetes.directo.calculadoras.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

    /**
     * default index / se redirecciona a la pagina principal de cetes
     * */
    @GetMapping("/")
    public RedirectView index(){
        return new RedirectView("cetes");
    }
}
