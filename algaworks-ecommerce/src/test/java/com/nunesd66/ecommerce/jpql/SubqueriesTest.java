package com.nunesd66.ecommerce.jpql;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Cliente;
import com.nunesd66.ecommerce.model.Pedido;
import com.nunesd66.ecommerce.model.Produto;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SubqueriesTest extends EntityManagerTest {

    @Test
    public void pesquisarSubqueries() {
        // bons clientes 2
        String jpql = "select c from Cliente c where " +
                "500 < (select sum(p.total) from Pedido p where p.cliente = c)";

        // bons clientes 1
//        String jpql = "select c from Cliente c where " +
//                "500 < (select sum(p.total) from c.pedidos p)";

        // todos os pedidos acima da media de vendas
//        String jpql = "select p from Pedido p where " +
//                "p.total > (select avg (total) from Pedido)";

        // o produto ou os produtos mais caros da base
//        String jpql = "select p from Produto p where " +
//                "p.preco = (select max(preco) from Produto)";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(obj.getNome()));
    }

}
