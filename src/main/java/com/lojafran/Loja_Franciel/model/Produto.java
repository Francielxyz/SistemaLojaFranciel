package com.lojafran.Loja_Franciel.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

	public Produto() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String descricao;
	
	private Double valorVenda;
	
	private String categoria;
	
	@ManyToOne
	private Marca marca;
	
	private Double quantidadeEstoque = 0.0;

}