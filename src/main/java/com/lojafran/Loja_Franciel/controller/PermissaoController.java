package com.lojafran.Loja_Franciel.controller;

import com.lojafran.Loja_Franciel.entity.Permissao;
import com.lojafran.Loja_Franciel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/administrativo/permissoes")
public class PermissaoController {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private PapelRepository papelRepository;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(Permissao permissao) {
		ModelAndView mv =  new ModelAndView("administrativo/permissoes/cadastro");
		mv.addObject("permissao",permissao);
		mv.addObject("listaFuncionarios",funcionarioRepository.findAll());
		mv.addObject("listaPapeis", papelRepository.findAll());
		return mv;
	}

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/permissoes/lista");
		mv.addObject("listaPermissoes", permissaoRepository.findAll());
		return mv;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Permissao> permissao = permissaoRepository.findById(id);
		return cadastrar(permissao.get());
	}


	@GetMapping("/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Permissao> permissao = permissaoRepository.findById(id);
		permissaoRepository.delete(permissao.get());
		return listar();
	}

	
	@PostMapping("/salvar")
	public ModelAndView salvar(@Validated Permissao permissao, BindingResult result) {

		if(result.hasErrors()) {
			return cadastrar(permissao);
		}
		permissaoRepository.saveAndFlush(permissao);

		return cadastrar(new Permissao());
	}

}
