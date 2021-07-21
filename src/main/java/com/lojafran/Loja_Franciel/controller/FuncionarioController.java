package com.lojafran.Loja_Franciel.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lojafran.Loja_Franciel.model.Funcionario;
import com.lojafran.Loja_Franciel.repository.FuncionarioRepository;


@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepositorio;
	
	
	@GetMapping("/administrativo/usuarios/cadastrar")
	public ModelAndView cadastrar(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("administrativo/usuarios/cadastro");
		mv.addObject("funcionario",funcionario);
		return mv;
	}
	
	@GetMapping("/administrativo/usuarios/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/usuarios/lista");
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		return mv;
	}
	
	@PostMapping("administrativo/funcionarios/salvar")
	public ModelAndView salvar(@Validated Funcionario funcionario, BindingResult result) {
		if(result.hasErrors()) {
	
			return cadastrar(funcionario);
		}
		
		funcionarioRepositorio.saveAndFlush(funcionario);
		
		return cadastrar(new Funcionario());
	}
	
}
