package com.lojafran.Loja_Franciel.controller;

import com.lojafran.Loja_Franciel.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/administrativo/index");
        mv.addObject("listaProdutos", produtoRepository.findAll());
        return mv;
    }

}
