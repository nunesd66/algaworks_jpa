package com.nunesd66.ecommerce.jpql;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Pedido;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class OperadoresLogicosTest extends EntityManagerTest {

    @Test
    public void usarOperadores() {
        String jpql = "select p from Pedido p where p.total > 100 and (p.status = 'AGUARDANDO' or p.status = 'PAGO')";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lsita = typedQuery.getResultList();

        assertFalse(lsita.isEmpty());
    }

}
