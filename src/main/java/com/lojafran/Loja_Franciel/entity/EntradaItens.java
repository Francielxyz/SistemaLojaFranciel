package com.lojafran.Loja_Franciel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "entrada_itens")
public class EntradaItens implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade = 0;

    private Double valorProduto = 0.0;

    private Double valorVenda = 0.0;

    @ManyToOne
    private EntradaProduto entradaProduto;

    @ManyToOne
    private Produto produto;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}