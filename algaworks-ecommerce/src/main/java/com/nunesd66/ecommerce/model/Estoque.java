package com.nunesd66.ecommerce.model;

import com.nunesd66.ecommerce.base.EntidadeBaseInteger;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estoque")
public class Estoque extends EntidadeBaseInteger {

    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_estoque_produto"))
    private Produto produto;

    @PositiveOrZero
    @NotNull
    @Column(nullable = false)
    private Integer quantidade;
}
