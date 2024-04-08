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
    public void pesquisarComExistsExercicio_9_40() {
        // pesquisar todos os produtos que nao foram vendidos ainda com o preco atual (preco do produto)
        String jpql = "select p from Produto p " +
                " where exists " +
                " (select 1 from ItemPedido where produto = p and precoProduto <> p.preco)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarSubqueriesExercicio_9_39() {
        // codigo da aula
//        String jpql = "select c from Cliente c where " +
//                " (select count(cliente) from Pedido where cliente = c) >= 2";

        // pesquisar todos os clientes que fizeram ao menos 2 pedidos
        String jpql = "select c from Cliente c where 2 <= (" +
                    "select count(p2) from Pedido p2 " +
                    "join p2.cliente c2 " +
                    "where c2.id = c.id" +
                ")";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(obj.getNome()));
    }

    @Test
    public void pesquisarComINExercicio_9_38() {
        String jpql = "select p from Pedido p where p.id in " +
                "(select p2.id from ItemPedido i2 " +
                "join i2.pedido p2 " +
                "join i2.produto pro2 " +
                "join pro2.categorias c2 " +
                "where c2.id = 2)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAllExercicio_9_43() {
        // todos os produtos que sempre foram vendidos pelo mesmo preco
        String jpql = "select p from Produto p where " +
                " 1 = ALL (select count(distinct precoProduto) from ItemPedido ip where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAll() {
        // todos os produtos que nao foram vendidos mais depois que encareceram
        String jpql = "select p from Produto p where " +
                "p.preco > ALL (select precoProduto from ItemPedido where produto = p)";
//        "p.preco > (select max(precoProduto) from ItemPedido where produto = p)";

        // todos os produtos que foram vendidos pelo preco atual
//        String jpql = "select p from Produto p where " +
//                "p.preco = ALL (select precoProduto from ItemPedido where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAny() {
        // todos os produtos que ja foram vendidos por um preco diferente do atual
        String jpql = "select p from Produto p " +
                "where p.preco <> ANY (select precoProduto from ItemPedido where produto = p)";
//                "where p.preco <> SOME (select precoProduto from ItemPedido where produto = p)";

        // todos os produtos que foram vendidos, pelo menos, uma vez pelo preco atual
//        String jpql = "select p from Produto p " +
//                "where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));

    }

    @Test
    public void pesquisarComExists() {
        String jpql = "select p from Produto p where exists (" +
                "select 1 from ItemPedido ip2 " +
                "join ip2.produto p2 " +
                "where p2 = p) " +
                "and p.dataCriacao < current_date";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComIN() {
        String jpql = "select p from Pedido p where p.id in " +
                "(select p2.id from ItemPedido i2 " +
                "join i2.pedido p2 " +
                "join i2.produto pro2 " +
                "where pro2.preco > 100)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

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
