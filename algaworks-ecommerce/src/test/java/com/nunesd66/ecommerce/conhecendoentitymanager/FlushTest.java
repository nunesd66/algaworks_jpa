package com.nunesd66.ecommerce.conhecendoentitymanager;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.enumeration.StatusPedido;
import com.nunesd66.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FlushTest extends EntityManagerTest {

    @Test
    public void chamarFlush() {
        assertThrows(Exception.class, this::erroAoChamarFlush);
    }

    private void erroAoChamarFlush() {
        try {
            entityManager.getTransaction().begin();

            Pedido pedido = entityManager.find(Pedido.class, 1);
            pedido.setStatus(StatusPedido.PAGO);

            entityManager.flush();

            if (pedido.getPagamento() == null) {
                throw new RuntimeException("Pedido ainda não foi pago.");
            }

            // Uma consulta obriga o JPA a sincronizar o que ele tem na memória (sem usar o flush explicitamente).
            // Pedido pedidoPago = entityManager
            //      .createQuery("select p from Pedido p where p.id = 1", Pedido.class)
            //      .getSingleResult();
            // Assertions.assertEquals(pedido.getStatus(), pedidoPago.getStatus());

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

}
