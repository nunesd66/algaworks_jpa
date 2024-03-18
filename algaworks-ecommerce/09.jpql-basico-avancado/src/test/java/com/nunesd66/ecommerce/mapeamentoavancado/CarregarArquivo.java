package com.nunesd66.ecommerce.mapeamentoavancado;

import java.io.IOException;

public class CarregarArquivo {

    public static byte[] carregarNotaFiscal() {
        try {
            return SalvandoArquivoTest.class.getResourceAsStream(
                    "/nota-fiscal.xml").readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static byte[] carregarProduto() {
        try {
            return SalvandoArquivoTest.class.getResourceAsStream(
                    "/betoneira400l.jpeg").readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
