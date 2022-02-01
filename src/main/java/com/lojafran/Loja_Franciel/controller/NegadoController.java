package com.lojafran.Loja_Franciel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NegadoController {

    @GetMapping("/negadoAdministrativo")
    public ModelAndView negadoAdministrativo() {
        ModelAndView mv =  new ModelAndView("administrativo/negadoAdministrativo");
        return mv;
    }

    @GetMapping("/negadoCliente")
    public ModelAndView negadoCliente() {
        ModelAndView mv =  new ModelAndView("cliente/negadoAdministrativo");
        return mv;
    }

}
