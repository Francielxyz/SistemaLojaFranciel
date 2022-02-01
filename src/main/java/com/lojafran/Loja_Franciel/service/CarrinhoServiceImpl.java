package com.lojafran.Loja_Franciel.service;

import com.lojafran.Loja_Franciel.entity.Compra;
import com.lojafran.Loja_Franciel.entity.ItensCompra;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CarrinhoServiceImpl implements CarrinhoService{

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
    public void buscarUsuarioLogado() {
//        Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
//        if (!(autenticado instanceof AnonymousAuthenticationToken)) {
//            String email = autenticado.getName();
//            cliente = clienteRepository.buscarClienteEmail(email).get(0);
//        }
    }

}
