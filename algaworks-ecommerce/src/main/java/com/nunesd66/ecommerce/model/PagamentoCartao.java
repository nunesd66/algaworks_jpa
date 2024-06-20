package com.nunesd66.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("cartao") // default values
@Entity
// @Table(name = "pagamento_cartao") // default values extends abstract @Table
public class PagamentoCartao extends Pagamento {

    @NotEmpty
    @Column(name = "numero_cartao", length = 50)
    private String numeroCartao;

}
