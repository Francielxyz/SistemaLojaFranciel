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
@Table(name = "entrada_itens")
public class EntradaItens implements Serializable {

	public EntradaItens() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private EntradaProduto entradaProduto;

	@ManyToOne
	private Produto produto;
	
	private Double quantidade = 0.0;
	
	private Double valorProduto = 0.0;
	private Double valorVenda = 0.0;
	
	
}