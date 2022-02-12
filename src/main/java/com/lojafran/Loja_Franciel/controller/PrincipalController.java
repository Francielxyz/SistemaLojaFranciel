package com.lojafran.Loja_Franciel.controller;

import com.lojafran.Loja_Franciel.entity.Compra;
import com.lojafran.Loja_Franciel.entity.Produto;
import com.lojafran.Loja_Franciel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PrincipalController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private PapelRepository papelRepository;

    @GetMapping("/administrativo")
    public ModelAndView acessarPrincipal() {
        ModelAndView mv = new ModelAndView("administrativo/home");
        mv.addObject("totalProdutos", produtoRepository.count());
        mv.addObject("totalProdutosEstoque", quantidadeEstoqueProduto());
        mv.addObject("totalMarcas", marcaRepository.count());
        mv.addObject("totalCategorias", categoriaRepository.count());
        mv.addObject("totalFuncionarios", funcionarioRepository.count());
        mv.addObject("totalPapeis", papelRepository.count());
        mv.addObject("totalCompras", compraRepository.count());
        mv.addObject("totalCompraVendidas", totalCompraVendidas());
        return mv;
    }

    public int quantidadeEstoqueProduto() {
        List<Produto> produtos = produtoRepository.findAll();

        int totalEstoque = 0;
        for (Produto produto : produtos) {
            totalEstoque += produto.getQuantidadeEstoque();
        }
        return totalEstoque;
    }

    public Double totalCompraVendidas() {
        List<Compra> compras = compraRepository.findAll();

        Double totalVendido = 0.0;
        for (Compra compra : compras) {
            totalVendido += compra.getValorTotal();
        }
        return totalVendido;
    }

}
