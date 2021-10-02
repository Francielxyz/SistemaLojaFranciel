package com.lojafran.Loja_Franciel.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "estrada_produto")
public class EntradaProduto implements Serializable {

	public EntradaProduto() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Funcionario funcionario;
	
	private Date dataEntrada = new Date();
	
	private String observacao;
	
	private String fornecedor;

}