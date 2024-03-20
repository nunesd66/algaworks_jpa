package com.nunesd66.ecommerce.jpql;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Pedido;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JoinTest  extends EntityManagerTest {

    @Test
    public void usarJoinFetch() {
        String jpql = "select p from Pedido p "
                + " left join fetch p.pagamento "
                + " join fetch p.cliente "
                + " left join fetch p.notaFiscal ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void fazerLeftJoin() {
        String jpql1 = "select p from Pedido p left join p.pagamento pag on pag.status = 'PROCESSANDO'";
        TypedQuery<Object[]> typedQuery1 = entityManager.createQuery(jpql1, Object[].class);
        List<Object[]> lista1 = typedQuery1.getResultList();
        assertEquals(2, lista1.size());
    }

    @Test
    public void fazerJoin() {
        String jpql1 = "select p from Pedido p";
        TypedQuery<Pedido> typedQuery1 = entityManager.createQuery(jpql1, Pedido.class);
        List<Pedido> lista1 = typedQuery1.getResultList();
        assertTrue(lista1.size() > 1);

        String jpql2 = "select p from Pedido p join p.pagamento pag";
        TypedQuery<Pedido> typedQuery2 = entityManager.createQuery(jpql2, Pedido.class);
        List<Pedido> lista2 = typedQuery2.getResultList();
        assertEquals(1, lista2.size());

        String jpql3 = "select p, pag from Pedido p join p.pagamento pag where pag.status = 'PROCESSANDO'";
        TypedQuery<Object[]> typedQuery3 = entityManager.createQuery(jpql3, Object[].class);
        List<Object[]> lista3 = typedQuery3.getResultList();
        assertEquals(1, lista3.size());

        String jpql4 = "select p from Pedido p join p.itens i where i.precoProduto < 500";
        TypedQuery<Object[]> typedQuery4 = entityManager.createQuery(jpql4, Object[].class);
        List<Object[]> lista4 = typedQuery4.getResultList();
        assertEquals(2, lista4.size());

        String jpql5 = "select p from Pedido p join p.itens i";
        TypedQuery<Object[]> typedQuery5 = entityManager.createQuery(jpql5, Object[].class);
        List<Object[]> lista5 = typedQuery5.getResultList();
        assertEquals(3, lista5.size());

        String jpql6 = "select p from Pedido p join p.itens i join i.produto pro where pro.id = 1";
        TypedQuery<Object[]> typedQuery6 = entityManager.createQuery(jpql6, Object[].class);
        List<Object[]> lista6 = typedQuery6.getResultList();
        assertEquals(2, lista6.size());

        // "select p from Pedido p join p.pagamento pag where pag.status = 'PROCESSANDO'"
        String jpql7 = "select p from Pedido p join p.pagamento pag on pag.status = 'PROCESSANDO'";
        TypedQuery<Object[]> typedQuery7 = entityManager.createQuery(jpql7, Object[].class);
        List<Object[]> lista7 = typedQuery7.getResultList();
        assertEquals(1, lista7.size());
    }

}
