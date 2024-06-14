package com.nunesd66.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@DiscriminatorValue("boleto") // default values
@Entity
// @Table(name = "pagamento_boleto") // default values extends abstract @Table
public class PagamentoBoleto extends Pagamento {

    @Column(name = "codigo_barras", length = 100)
    private String codigoBarras;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

}
