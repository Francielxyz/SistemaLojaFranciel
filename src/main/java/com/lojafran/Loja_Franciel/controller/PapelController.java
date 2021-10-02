package com.lojafran.Loja_Franciel.controller;

import com.lojafran.Loja_Franciel.entity.Papel;
import com.lojafran.Loja_Franciel.repository.PapelRepository;
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
@RequestMapping("/administrativo/papeis")
public class PapelController {

	@Autowired
	private PapelRepository papelRepository;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(Papel papel) {
		ModelAndView mv =  new ModelAndView("administrativo/papeis/cadastro");
		mv.addObject("papel",papel);
		return mv;
	}

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/papeis/lista");
		mv.addObject("listaPapeis", papelRepository.findAll());
		return mv;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Papel> papel = papelRepository.findById(id);
		return cadastrar(papel.get());
	}


	@GetMapping("/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Papel> papel = papelRepository.findById(id);
		papelRepository.delete(papel.get());
		return listar();
	}


	@PostMapping("/salvar")
	public ModelAndView salvar(@Validated Papel papel, BindingResult result) {

		if(result.hasErrors()) {
			return cadastrar(papel);
		}
		papelRepository.saveAndFlush(papel);

		return cadastrar(new Papel());
	}


}
