package com.nunesd66.ecommerce.detalhesimportantes;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OneOneLazyTest extends EntityManagerTest {

    @Test
    public void mostrarProblema() {
        System.out.println("BUSCANDO UM PEDIDO:");
        Pedido pedido = entityManager
                .createQuery("select p from Pedido p " +
                        "left join fetch p.pagamento " +
                        "left join fetch p.cliente " +
                        "left join fetch p.notaFiscal " +
                        "where p.id = 1", Pedido.class)
                .getSingleResult();
        assertNotNull(pedido);

        System.out.println("----------------------------------------------------");

        System.out.println("BUSCANDO UMA LISTA DE PEDIDOS:");
        List<Pedido> lista = entityManager
                .createQuery("select p from Pedido p " +
                        "left join fetch p.pagamento " +
                        "left join fetch p.cliente " +
                        "left join fetch p.notaFiscal", Pedido.class)
                .getResultList();
        assertFalse(lista.isEmpty());
    }
}
