package com.nunesd66.ecommerce.model;

import com.nunesd66.ecommerce.base.EntidadeBaseInteger;
import com.nunesd66.ecommerce.embeddable.EnderecoEntregaPedido;
import com.nunesd66.ecommerce.enumeration.StatusPedido;
import com.nunesd66.ecommerce.listener.GenericoListener;
import com.nunesd66.ecommerce.listener.GerarNotaFiscalListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Pedido.dadosEssenciais",
                attributeNodes = {
                        @NamedAttributeNode("dataCriacao"),
                        @NamedAttributeNode("status"),
                        @NamedAttributeNode("total"),
                        @NamedAttributeNode(
                                value = "cliente",
                                subgraph = "cli"
                        ),
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "cli",
                                attributeNodes = {
                                        @NamedAttributeNode("nome"),
                                        @NamedAttributeNode("cpf")
                                }
                        )
                }
        )
})
@EntityListeners({GerarNotaFiscalListener.class, GenericoListener.class})
@Cacheable()
@Entity
@Table(name = "pedido")
public class Pedido extends EntidadeBaseInteger
//        implements PersistentAttributeInterceptable
{

    @NotNull
    @ManyToOne(optional = false)
//    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
    private Cliente cliente;

    @NotEmpty
    @OneToMany(mappedBy = "pedido")
//    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    @OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
//    @OneToMany(mappedBy = "pedido", cascade = CascadeType.MERGE)
//    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
    private List<ItemPedido> itens;

    @PastOrPresent
    @NotNull
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @PastOrPresent
    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @PastOrPresent
    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

//        @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "pedido"
//            , fetch = FetchType.LAZY
    )
    private NotaFiscal notaFiscal;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal total;

    @NotNull
    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

//        @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "pedido"
//            , fetch = FetchType.LAZY
    )
    private Pagamento pagamento;

    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;

//    public NotaFiscal getNotaFiscal() {
//        if (this.persistentAttributeInterceptor != null) {
//            return (NotaFiscal) persistentAttributeInterceptor
//                    .readObject(this, "notaFiscal", this. notaFiscal);
//        }
//
//        return this.notaFiscal;
//    }
//
//    public void setNotaFiscal(NotaFiscal notaFiscal) {
//        if (this.persistentAttributeInterceptor != null) {
//            this.notaFiscal = (NotaFiscal) persistentAttributeInterceptor
//                    .writeObject(this, "notaFiscal", this.notaFiscal, notaFiscal);
//        } else {
//            this.notaFiscal = notaFiscal;
//        }
//    }
//
//    public Pagamento getPagamento() {
//        if (this.persistentAttributeInterceptor != null) {
//            return (Pagamento) persistentAttributeInterceptor
//                    .readObject(this, "pagamento", this.pagamento);
//        }
//
//        return this.pagamento;
//    }
//
//    public void setPagamento(Pagamento pagamento) {
//        if (this.persistentAttributeInterceptor != null) {
//            this.pagamento = (Pagamento) persistentAttributeInterceptor
//                    .writeObject(this, "pagamento", this.pagamento, pagamento);
//        } else {
//            this.pagamento = pagamento;
//        }
//    }

//    @Setter(AccessLevel.NONE)
//    @Getter(AccessLevel.NONE)
//    @Transient
//    private PersistentAttributeInterceptor persistentAttributeInterceptor;
//
//    @Override
//    public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
//        return this.persistentAttributeInterceptor;
//    }
//
//    @Override
//    public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor persistentAttributeInterceptor) {
//        this.persistentAttributeInterceptor = persistentAttributeInterceptor;
//    }
//
    public boolean isPago() {
        return StatusPedido.PAGO.equals(status);
    }

    public void calcularTotal() {
        if (itens != null) {
            total = itens.stream().map(
                    i -> new BigDecimal(i.getQuantidade()).multiply(i.getPrecoProduto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            total = BigDecimal.ZERO;
        }
    }

    @PrePersist
    public void aoPersistir() {
        dataCriacao = LocalDateTime.now();
        calcularTotal();
    }

    @PreUpdate
    public void aoAtualizar() {
        dataUltimaAtualizacao = LocalDateTime.now();
        calcularTotal();
    }

    @PostPersist
    public void aposPersistir() {
        System.out.println("Após persistir Pedido.");
    }

    @PostUpdate
    public void aposAtualizar() {
        System.out.println("Após atualizar Pedido.");
    }

    @PreRemove
    public void aoRemover() {
        System.out.println("Antes de remover Pedido.");
    }

    @PostRemove
    public void aposRemover() {
        System.out.println("Após remover Pedido.");
    }

    @PostLoad
    public void aposCarregar() {
        System.out.println("Após carregar a entidade.");
    }

}
