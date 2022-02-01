package com.lojafran.Loja_Franciel.service;

import com.lojafran.Loja_Franciel.entity.Cliente;
import com.lojafran.Loja_Franciel.entity.Compra;
import com.lojafran.Loja_Franciel.entity.ItensCompra;
import com.lojafran.Loja_Franciel.repository.ClienteRepository;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CarrinhoServiceImpl implements CarrinhoService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<ItensCompra> calcularTotal(List<ItensCompra> listItensCompras) {
        Compra compra = new Compra();
        compra.setValorTotal(0.);
        for (ItensCompra it : listItensCompras) {
            compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
        }

        return listItensCompras;
    }

    @Override
    public Cliente buscarUsuarioLogado(Cliente cliente) {
        Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
        if (!(autenticado instanceof AnonymousAuthenticationToken)) {
            String email = autenticado.getName();
            cliente = clienteRepository.buscarClienteEmail(email).get(0);
        }
        return cliente;
    }

}
