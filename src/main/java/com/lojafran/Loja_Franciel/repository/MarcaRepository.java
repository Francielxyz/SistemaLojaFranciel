package com.lojafran.Loja_Franciel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lojafran.Loja_Franciel.entity.Marca;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long>{

    @Query("from Marca m where m.nome like %?1% ")
    List<Marca> findByNome(String nome);

}
