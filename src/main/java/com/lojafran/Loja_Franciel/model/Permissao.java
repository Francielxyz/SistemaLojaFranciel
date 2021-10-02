package com.lojafran.Loja_Franciel.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "permissoes")
public class Permissao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dataCadastro = new Date();

	@ManyToOne
	private Funcionario funcionario;

	@ManyToOne
	private Papel papel;
}