package com.nunesd66.ecommerce.criteria;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.enumeration.StatusPedido;
import com.nunesd66.ecommerce.model.Pedido;
import com.nunesd66.ecommerce.model.Pedido_;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class OperadoresLogicosCriteriaTest extends EntityManagerTest {

    @Test
    public void usarOperadoresLogicos() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
                criteriaBuilder.or(
                        criteriaBuilder.equal(
                                root.get(Pedido_.status), StatusPedido.AGUARDANDO),
                        criteriaBuilder.equal(
                                root.get(Pedido_.status), StatusPedido.PAGO)
                ),
                criteriaBuilder.greaterThan(
                        root.get(Pedido_.total), new BigDecimal(499))
        );

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        lista.forEach(p -> System.out.println(p.getId() + " - " + p.getTotal() + " - " + p.getStatus().toString()));
    }

}
