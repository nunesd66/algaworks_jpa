package com.nunesd66.ecommerce.model;

import com.nunesd66.ecommerce.embeddable.ItemPedidoId;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "item_pedido-produto.ItemPedido-Produto",
                entities = { @EntityResult(entityClass = ItemPedido.class),
                        @EntityResult(entityClass = Produto.class) })
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @EmbeddedId()
    private ItemPedidoId id;

    @NotNull
    @MapsId("pedidoId")
    @ManyToOne(optional = false)
//    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
//    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "pedido_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_item_pedido_pedido"))
    private Pedido pedido;

    @NotNull
    @MapsId("produtoId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_item_pedido_produto"))
    private Produto produto;

    @Positive
    @NotNull
    @Column(name = "preco_produto", nullable = false)
    private BigDecimal precoProduto;

    @Positive
    @NotNull
    @Column(nullable = false)
    private Integer quantidade;
}
