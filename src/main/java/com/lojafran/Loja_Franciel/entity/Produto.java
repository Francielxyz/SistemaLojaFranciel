package com.lojafran.Loja_Franciel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private Double valorVenda;

    private Double quantidadeEstoque = 0.0;

    private String nomeImagem;

    @ManyToOne
    private Marca marca;

    @ManyToOne
    private Categoria categoria;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}