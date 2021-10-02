package com.lojafran.Loja_Franciel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lojafran.Loja_Franciel.entity.EntradaItens;
import com.lojafran.Loja_Franciel.entity.EntradaProduto;
import com.lojafran.Loja_Franciel.entity.Produto;
import com.lojafran.Loja_Franciel.repository.EntradaItensRepository;
import com.lojafran.Loja_Franciel.repository.EntradaProdutoRepository;
import com.lojafran.Loja_Franciel.repository.FuncionarioRepository;
import com.lojafran.Loja_Franciel.repository.ProdutoRepository;

@Controller
@RequestMapping("/administrativo/entrada")
public class EntradaProdutoController {
	
	private List<EntradaItens> listaEntradaItens = new ArrayList<>();

	@Autowired
	private EntradaProdutoRepository entradaProdutoRepository;

	@Autowired
	private EntradaItensRepository entradaItensRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(EntradaProduto entradaProduto, EntradaItens entradaItens) {
		ModelAndView mv =  new ModelAndView("administrativo/entrada/cadastro");
		mv.addObject("entrada", entradaProduto);
		mv.addObject("listaEntradaItens", this.listaEntradaItens);
		mv.addObject("entradaItens", entradaItens);
		mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
		mv.addObject("listaProdutos", produtoRepository.findAll());
		return mv;
	}

//	@GetMapping("/listar")
//	public ModelAndView listar() {
//		ModelAndView mv=new ModelAndView("administrativo/cidades/lista");
//		mv.addObject("listaCidades", cidadeRepository.findAll());
//		return mv;
//	}

//	@GetMapping("/editar/{id}")
//	public ModelAndView editar(@PathVariable("id") Long id) {
//		Optional<Cidade> cidade = cidadeRepository.findById(id);
//		return cadastrar(cidade.get());
//	}

//	@GetMapping("/remover/{id}")
//	public ModelAndView remover(@PathVariable("id") Long id) {
//		Optional<Cidade> cidade = cidadeRepository.findById(id);
//		cidadeRepository.delete(cidade.get());
//		return listar();
//	}

	@PostMapping("/salvar")
	public ModelAndView salvar(String acao, EntradaProduto entrada, EntradaItens entradaItens) {

		if (acao.equals("itens")) {
			this.listaEntradaItens.add(entradaItens);
		} else if (acao.equals("salvar")) {
			entradaProdutoRepository.saveAndFlush(entrada);
			for (EntradaItens it : listaEntradaItens) {
				it.setEntradaProduto(entrada);
				
				entradaItensRepository.saveAndFlush(it);
				
				Optional<Produto> prod = produtoRepository.findById(it.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + it.getQuantidade());
				produto.setValorVenda(it.getValorVenda());
				
				
				produtoRepository.saveAndFlush(produto);
				this.listaEntradaItens = new ArrayList<>();
			}
			return cadastrar(new EntradaProduto(), new EntradaItens());
		}

		return cadastrar(entrada, new EntradaItens());
	}

}
