package com.lojafran.Loja_Franciel.service;

import com.lojafran.Loja_Franciel.entity.Cliente;
import com.lojafran.Loja_Franciel.entity.ItensCompra;

import java.util.List;

public interface CarrinhoService {

    List<ItensCompra> calcularTotal(List<ItensCompra> listItensCompras);

    Cliente buscarUsuarioLogado(Cliente cliente);
}