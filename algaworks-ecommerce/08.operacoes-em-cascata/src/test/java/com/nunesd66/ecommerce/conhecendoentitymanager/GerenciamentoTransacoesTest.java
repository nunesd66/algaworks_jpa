package com.nunesd66.ecommerce.conhecendoentitymanager;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.enumeration.StatusPedido;
import com.nunesd66.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GerenciamentoTransacoesTest extends EntityManagerTest {

    @Test
    public void abrirFecharCancelarTransacao() {
        assertThrows(Exception.class, this::erroEsperadoMetodoDeNegocio);
    }

    private void erroEsperadoMetodoDeNegocio() {
        try {
            entityManager.getTransaction().begin();
            metodoDeNegocio();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    private void metodoDeNegocio() {
        Pedido pedido = entityManager.find(Pedido.class, 1);
        pedido.setStatus(StatusPedido.PAGO);

        if (pedido.getPagamento() == null) {
            throw new RuntimeException("Pedido ainda não foi pago");
        }
    }

}
