package com.nunesd66.ecommerce.model;

import com.nunesd66.ecommerce.base.EntidadeBaseInteger;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categoria")
public class Categoria extends EntidadeBaseInteger {

    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_pai_id")
    private Categoria categoriaPai;

    @OneToMany(mappedBy = "categoriaPai")
    private List<Categoria> categorias;

    @ManyToMany(mappedBy = "categorias")
    private List<Produto> produtos;
}
