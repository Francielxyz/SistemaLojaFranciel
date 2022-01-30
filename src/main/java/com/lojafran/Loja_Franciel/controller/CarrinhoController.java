package com.lojafran.Loja_Franciel.controller;

import com.lojafran.Loja_Franciel.entity.Compra;
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
    private Compra compra = new Compra();
    private List<ItensCompra> listItensCompras = new ArrayList<>();

    @Autowired
    private ProdutoRepository produtoRepository;


    private void calcularTotal() {
        compra.setValorTotal(0.);
        for (ItensCompra it : listItensCompras) {
            compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
        }
    }


    @GetMapping("/carrinho")
    public ModelAndView carrinho() {
        ModelAndView mv = new ModelAndView("/cliente/carrinho");
        calcularTotal();
        mv.addObject("compra", compra);
        mv.addObject("listaItens", listItensCompras);
        return mv;
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
        ModelAndView mv = new ModelAndView("/cliente/carrinho");

        for (ItensCompra it : listItensCompras) {
            if (it.getProduto().getId().equals(id)) {
                if (acao.equals(1)) {
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.setValorTotal(0.0);
                    it.setValorTotal(it.getValorTotal() + it.getQuantidade() * it.getValorUnitario());
                } else if (acao == 0 && it.getQuantidade() > 0) {
                    it.setQuantidade(it.getQuantidade() - 1);
                    it.setValorTotal(0.0);
                    it.setValorTotal(it.getValorTotal() + it.getQuantidade() * it.getValorUnitario());
                }
                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/removerProduto/{id}")
    public String removerProdutoCarrinho(@PathVariable Long id) {

        for (ItensCompra it : listItensCompras) {
            if (it.getProduto().getId().equals(id)) {
                listItensCompras.remove(it);
                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public String  adicionarCarrinho(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("/cliente/carrinho");
        Optional<Produto> produto = produtoRepository.findById(id);

        int controle = 0;
        for(ItensCompra ic : listItensCompras){
            if(ic.getProduto().getId().equals(produto.get().getId())){
                controle = 1;
                ic.setQuantidade(ic.getQuantidade() + 1);
                ic.setValorTotal(0.0);
                ic.setValorTotal(ic.getValorTotal() + ic.getQuantidade() * ic.getValorUnitario());
                break;
            }
        }

        if (!produto.isEmpty() && controle == 0) {
            ItensCompra itensCompra = new ItensCompra();
            itensCompra.setProduto(produto.get());
            itensCompra.setValorUnitario(produto.get().getValorVenda());
            itensCompra.setQuantidade(itensCompra.getQuantidade() + 1);
            itensCompra.setValorTotal(itensCompra.getValorTotal() + itensCompra.getQuantidade() * itensCompra.getValorUnitario());

            listItensCompras.add(itensCompra);
        }

        return "redirect:/carrinho";
    }
}
