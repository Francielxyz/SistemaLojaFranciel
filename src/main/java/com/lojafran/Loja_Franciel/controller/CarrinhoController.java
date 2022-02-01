package com.lojafran.Loja_Franciel.controller;

import com.lojafran.Loja_Franciel.entity.Cliente;
import com.lojafran.Loja_Franciel.entity.Compra;
import com.lojafran.Loja_Franciel.entity.ItensCompra;
import com.lojafran.Loja_Franciel.entity.Produto;
import com.lojafran.Loja_Franciel.repository.ClienteRepository;
import com.lojafran.Loja_Franciel.repository.CompraRepository;
import com.lojafran.Loja_Franciel.repository.ItensCompraRepository;
import com.lojafran.Loja_Franciel.repository.ProdutoRepository;
import com.lojafran.Loja_Franciel.service.CarrinhoService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CarrinhoController {
    private Compra compra = new Compra();
    private Cliente cliente;
    private List<ItensCompra> listItensCompras = new ArrayList<>();

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ItensCompraRepository itensCompraRepository;

    private final CarrinhoService carrinhoService;

    private void buscarUsuarioLogado() {
        Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
        if (!(autenticado instanceof AnonymousAuthenticationToken)) {
            String email = autenticado.getName();
            cliente = clienteRepository.buscarClienteEmail(email).get(0);
        }
    }

    @GetMapping("/carrinho")
    public ModelAndView carrinho() {
        ModelAndView mv = new ModelAndView("/cliente/carrinho");
        listItensCompras = carrinhoService.calcularTotal(listItensCompras);
        mv.addObject("compra", compra);
        mv.addObject("listaItens", listItensCompras);
        return mv;
    }

    @GetMapping("/finalizar")
    public ModelAndView finalizarCompra() {
        buscarUsuarioLogado();
        ModelAndView mv = new ModelAndView("cliente/finalizar");
        listItensCompras = carrinhoService.calcularTotal(listItensCompras);
        mv.addObject("compra", compra);
        mv.addObject("listaItens", listItensCompras);
        mv.addObject("cliente", cliente);
        return mv;
    }

    @PostMapping("/finalizar/confirmar")
    public ModelAndView confirmarCompra(String formaPagamento) {
        ModelAndView mv = new ModelAndView("cliente/mensagemFinalizou");
        compra.setCliente(cliente);
        compra.setFormaPagamento(formaPagamento);
        compraRepository.saveAndFlush(compra);

        for (ItensCompra c : listItensCompras) {
            c.setCompra(compra);
            itensCompraRepository.saveAndFlush(c);
        }
        listItensCompras = new ArrayList<>();
        compra = new Compra();
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
    public String adicionarCarrinho(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("/cliente/carrinho");
        Optional<Produto> produto = produtoRepository.findById(id);

        int controle = 0;
        for (ItensCompra ic : listItensCompras) {
            if (ic.getProduto().getId().equals(produto.get().getId())) {
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
