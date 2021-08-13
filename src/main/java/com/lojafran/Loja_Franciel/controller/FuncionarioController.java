package com.lojafran.Loja_Franciel.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lojafran.Loja_Franciel.model.Funcionario;
import com.lojafran.Loja_Franciel.repository.FuncionarioRepository;


@Controller
@RequestMapping("/administrativo/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepositorio;
	//melhorar forma do caminho (rota) relacionando ao controler
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
		mv.addObject("funcionario",funcionario);
		return mv;
	}
	
	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/funcionarios/lista");
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
		return cadastrar(funcionario.get());
	}
	
	@GetMapping("/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
		funcionarioRepositorio.delete(funcionario.get());
		return listar();
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@Validated Funcionario funcionario, BindingResult result) {
		if(result.hasErrors()) {
			return cadastrar(funcionario);
		}
		
		funcionarioRepositorio.saveAndFlush(funcionario);
		return cadastrar(new Funcionario());
	}
	
}