package com.lojafran.Loja_Franciel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class NegadoController {

    @GetMapping("/negado")
    public ModelAndView negado() {
        ModelAndView mv =  new ModelAndView("/negado");
        return mv;
    }

}
