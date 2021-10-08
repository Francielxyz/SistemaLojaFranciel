package com.lojafran.Loja_Franciel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView cadastrar() {
        ModelAndView mv = new ModelAndView("/administrativo/login");

        return mv;
    }

}
