package com.lojafran.Loja_Franciel.repository;

import com.lojafran.Loja_Franciel.entity.Categoria;
import com.lojafran.Loja_Franciel.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lojafran.Loja_Franciel.entity.Produto;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    @Query("from Produto p where p.descricao like %?1% ")
    List<Produto> findByDescricao(String descricao);

    List<Produto> findByCategoria(Categoria categoria);

    List<Produto> findByMarca(Marca marca);
}
