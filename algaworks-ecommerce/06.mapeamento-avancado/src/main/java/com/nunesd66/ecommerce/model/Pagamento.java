package com.nunesd66.ecommerce.model;

import com.nunesd66.ecommerce.base.EntidadeBaseInteger;
import com.nunesd66.ecommerce.enumeration.StatusPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorColumn(name = "tipo_pagamento",
        discriminatorType = DiscriminatorType.STRING) // default values, não é usando quando Inheritance.strategy for TABLE_PER_CLASS
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // default value: SINGLE_TABLE
@Entity
@Table(name = "pagamento") // não é usando quando Inheritance.strategy for TABLE_PER_CLASS
public abstract class Pagamento extends EntidadeBaseInteger {

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

}
