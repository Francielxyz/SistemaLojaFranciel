package com.lojafran.Loja_Franciel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class LoginController {

    @GetMapping("/negado")
    public ModelAndView login() {
        ModelAndView mv =  new ModelAndView("/login");

        return mv;
    }

}
