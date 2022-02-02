package com.lojafran.Loja_Franciel.repository;

import com.lojafran.Loja_Franciel.entity.Imagem;
import com.lojafran.Loja_Franciel.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagemRepository extends JpaRepository<Imagem, Long>{

    List<Imagem> findImagemByProduto(Produto produto);
}
