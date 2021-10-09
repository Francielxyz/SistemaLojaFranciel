package com.lojafran.Loja_Franciel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permissoes")
public class Permissao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dataCadastro = new Date();

	@ManyToOne
	private Funcionario funcionario;

	@ManyToOne
	private Papel papel;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}