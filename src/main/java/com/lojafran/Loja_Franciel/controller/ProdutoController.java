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

import com.lojafran.Loja_Franciel.model.Produto;
import com.lojafran.Loja_Franciel.repository.ProdutoRepository;

@Controller
@RequestMapping("/administrativo/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(Produto produto) {
		ModelAndView mv =  new ModelAndView("administrativo/produtos/cadastro");
		mv.addObject("produto", produto);
		return mv;
	}

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/produtos/lista");
		mv.addObject("listaProdutos", produtoRepository.findAll());
		return mv;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		return cadastrar(produto.get());
	}

	@GetMapping("/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		produtoRepository.delete(produto.get());
		return listar();
	}

	
	@PostMapping("/salvar")
	public ModelAndView salvar(@Validated Produto produto, BindingResult result) {
		
		if(result.hasErrors()) {
			return cadastrar(produto);
		}
		produtoRepository.saveAndFlush(produto);
		
		return cadastrar(new Produto());
	}

}
