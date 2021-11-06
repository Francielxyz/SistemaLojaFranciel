package com.lojafran.Loja_Franciel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lojafran.Loja_Franciel.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Funcionario findByEmail(String email);

    Funcionario findByEmailAndCodigoRecuperacao(String email, String codigoRecuperacao);

}
