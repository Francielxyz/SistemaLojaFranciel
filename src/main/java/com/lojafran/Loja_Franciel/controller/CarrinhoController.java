package com.lojafran.Loja_Franciel.controller;

import com.lojafran.Loja_Franciel.entity.ItensCompra;
import com.lojafran.Loja_Franciel.entity.Produto;
import com.lojafran.Loja_Franciel.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarrinhoController {

    private List<ItensCompra> listItensCompras = new ArrayList<>();

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/carrinho")
    public ModelAndView carrinho() {
        ModelAndView mv = new ModelAndView("/cliente/carrinho");
        mv.addObject("listaItens", listItensCompras);
        return mv;
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public ModelAndView adicionarCarrinho(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("/cliente/carrinho");
        Optional<Produto> produto = produtoRepository.findById(id);

        int controle = 0;
        for(ItensCompra ic : listItensCompras){
            if(ic.getProduto().getId().equals(produto.get().getId())){
                controle = 1;
                ic.setQuantidade(ic.getQuantidade() + 1);
                break;
            }
        }

        if (!produto.isEmpty() && controle == 0) {
            ItensCompra itensCompra = new ItensCompra();
            itensCompra.setProduto(produto.get());
            itensCompra.setValorUnitario(produto.get().getValorVenda());
            itensCompra.setQuantidade(itensCompra.getQuantidade() + 1);
            itensCompra.setValorTotal(itensCompra.getQuantidade() * itensCompra.getValorUnitario());

            listItensCompras.add(itensCompra);
        }

        mv.addObject("listaItens", listItensCompras);
        return mv;
    }
}
