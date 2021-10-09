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
@Table(name = "funcionario")
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double salarioBruto;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String uf;

    private String cep;

    private String email;

    private String senha;

	private String cargo;

	@Temporal(TemporalType.DATE)
	private Date dataEntrada;

	@Temporal(TemporalType.DATE)
	private Date dataSaida;

	@ManyToOne
	private Cidade cidade;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
