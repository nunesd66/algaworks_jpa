package com.nunesd66.ecommerce.model;

import com.nunesd66.ecommerce.base.EntidadeBaseInteger;
import com.nunesd66.ecommerce.embeddable.Atributo;
import com.nunesd66.ecommerce.listener.GenericoListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.Length.LONG;

@Getter
@Setter
@EntityListeners({GenericoListener.class})
@Entity
@Table(name = "produto",
        uniqueConstraints = { @UniqueConstraint(name = "unq_nome", columnNames = { "nome" }) },
        indexes = { @Index(name = "idx_nome", columnList = "nome") })
public class Produto extends EntidadeBaseInteger {

    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(columnDefinition = "varchar(275) default 'descricao'")
    private String descricao;

    private BigDecimal preco;

    @Lob
    @Column(length = LONG)
    private byte[] foto;

    @ManyToMany
    @JoinTable(name = "produto_categoria",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorias;

    @OneToOne(mappedBy = "produto")
    private Estoque estoque;

    @ElementCollection
    @CollectionTable(name = "produto_tag",
            joinColumns = @JoinColumn(name = "produto_id"))
    @Column(name = "tag", length = 50, nullable = false)
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "produto_atributo",
            joinColumns = @JoinColumn(name = "produto_id"))
    private List<Atributo> atributos;
}
