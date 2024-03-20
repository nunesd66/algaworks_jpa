package com.nunesd66.ecommerce.conhecendoentitymanager;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;

public class CachePrimeiroNivelTest extends EntityManagerTest {

    @Test
    public void verificarCache() {
        Produto produto = entityManager.find(Produto.class, 1);

        System.out.println("----------------------------------------");
        System.out.println(produto.getNome());
        System.out.println("----------------------------------------");

        // entityManager.clear(); // limpa o cache do entityManager

        // entityManager.close(); // fecha a instanciação do entityManager (e consequentemente limpa seu cache)
        // entityManager = entityManagerFactory.createEntityManager(); // após fechar, é necessário instanciar novamente

        Produto produtoResgatado = entityManager.find(Produto.class, produto.getId());
        System.out.println(produtoResgatado.getNome());
        System.out.println("----------------------------------------");
    }

}
