package com.nunesd66.ecommerce.detalhesimportantes;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConversorTest extends EntityManagerTest {

    @Test
    public void converter() {
        Produto produto = new Produto();
        produto.setDataCriacao(LocalDateTime.now());
        produto.setNome("Carregador de Notebook Dell");
        produto.setAtivo(true);

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertTrue(produtoVerificacao.getAtivo());
    }

}
