package com.nunesd66.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@DiscriminatorValue("boleto") // default values
@Entity
// @Table(name = "pagamento_boleto") // default values extends abstract @Table
public class PagamentoBoleto extends Pagamento {

    @NotBlank
    @Column(name = "codigo_barras", length = 100)
    private String codigoBarras;

    @NotNull
    @FutureOrPresent
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

}
