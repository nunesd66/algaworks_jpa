package com.nunesd66.ecommerce.mapeamentoavancado;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static com.nunesd66.ecommerce.mapeamentoavancado.CarregarArquivo.carregarNotaFiscal;
import static com.nunesd66.ecommerce.mapeamentoavancado.CarregarArquivo.carregarProduto;

public class SalvandoArquivoTest  extends EntityManagerTest {

    @Test
    public void salvarXmlNota() {
        Pedido pedido = entityManager.find(Pedido.class, 1);

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setPedido(pedido);
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setXml(carregarNotaFiscal());

        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();

        entityManager.clear();

        NotaFiscal notaFiscalVerificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
        Assertions.assertNotNull(notaFiscalVerificacao.getXml());
        Assertions.assertTrue(notaFiscalVerificacao.getXml().length > 0);

//        try {
//            OutputStream out = new FileOutputStream(
//                    Files.createFile(Paths.get(
//                            System.getProperty("user.home") + "/Downloads/xml.xml")).toFile());
//            out.write(notaFiscalVerificacao.getXml());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    @Test
    public void salvarProduto() {
        Produto produto = new Produto();
        produto.setDataCriacao(LocalDateTime.now());
        produto.setNome("Betoneira");
        produto.setDescricao("Betoneira 400L 2CV 4 Polos Monofásica com Kit de Segurança - MENEGOTTI-PRIME400");
        produto.setPreco(new BigDecimal("4389.00"));
        produto.setFoto(carregarProduto());

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assertions.assertNotNull(produtoVerificacao.getFoto());
        Assertions.assertTrue(produtoVerificacao.getFoto().length > 0);
    }

}
