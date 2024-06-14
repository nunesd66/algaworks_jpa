package com.nunesd66.ecommerce.criteria;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.embeddable.ItemPedidoId_;
import com.nunesd66.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SubqueriesCriteriaTest extends EntityManagerTest {

    @Test
    public void pesquisarComAllExercicio() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(criteriaBuilder.equal(
                        root.get(Produto_.preco), criteriaBuilder.all(subquery)),
                criteriaBuilder.exists(subquery)
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(p -> System.out.println("produto: " + p.getId()));
    }

    @Test
    public void pesquisarComAllExercicioResolvido() {
//        Todos os produtos que sempre foram vendidos pelo mesmo preço. - Resolvido
//        String jpql = "select distinct p from ItemPedido ip join ip.produto p where " +
//                " ip.precoProduto = ALL " +
//                " (select precoProduto from ItemPedido where produto = p and id <> ip.id)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);

        criteriaQuery.select(root.get(ItemPedido_.produto));
        criteriaQuery.distinct(true);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
        subquery.where(
                criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root.get(ItemPedido_.produto)),
                criteriaBuilder.notEqual(subqueryRoot, root)
        );

        criteriaQuery.where(
                criteriaBuilder.equal(
                        root.get(ItemPedido_.precoProduto), criteriaBuilder.all(subquery))
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAny02() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(criteriaBuilder.notEqual(root.get(Produto_.preco), criteriaBuilder.any(subquery)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(p -> System.out.println("produto: " + p.getId()));
    }

    @Test
    public void pesquisarComAny01() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.preco), criteriaBuilder.any(subquery)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(p -> System.out.println("produto: " + p.getId()));
    }

    @Test
    public void pesquisarComAll02() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(criteriaBuilder.greaterThan(
                root.get(Produto_.preco), criteriaBuilder.all(subquery)),
                criteriaBuilder.exists(subquery)
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(p -> System.out.println("produto: " + p.getId()));
    }

    @Test
    public void pesquisarComAll01() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(criteriaBuilder.equal(
                root.get(Produto_.preco), criteriaBuilder.all(subquery)
        ));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(p -> System.out.println("produto: " + p.getId()));
    }

    @Test
    public void pesquisarComExistsExercicio() {
        // todos os produtos que foram vendidos por um preço diferente do preço atual

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(
                criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root),
                criteriaBuilder.notEqual(subqueryRoot.get(ItemPedido_.precoProduto), root.get(Produto_.preco))
        );

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(p -> System.out.println("produto: " + p.getId()));
    }

    @Test
    public void pesquisarComExistsExercicioResolvido() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(
                criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root),
                criteriaBuilder.notEqual(
                        subqueryRoot.get(ItemPedido_.precoProduto), root.get(Produto_.preco))
        );

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComInExercicio() {
        // todos os pedidos com algum produto da categoria de id = 2

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        Join<ItemPedido, Pedido> subqueryJoinPedido = subqueryRoot.join(ItemPedido_.pedido);
        Join<ItemPedido, Produto> subqueryJoinProduto = subqueryRoot.join(ItemPedido_.produto);
        Join<Produto, Categoria> subqueryJoinCategoria = subqueryJoinProduto.join(Produto_.categorias);

        subquery.select(subqueryJoinPedido.get(Pedido_.id));
        subquery.where(criteriaBuilder.equal(subqueryJoinCategoria.get(Categoria_.id), 8));

        criteriaQuery.where(root.get(Pedido_.id).in(subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> pedidos = typedQuery.getResultList();
        assertFalse(pedidos.isEmpty());

        pedidos.forEach(c -> System.out.println("pedido: " + c.getId()));
    }

    @Test
    public void pesquisarComINExercicioResolvido() {
        // todos os pedidos com algum produto da categoria de id = 2 - resolvido

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        Join<ItemPedido, Produto> subqueryJoinProduto = subqueryRoot.join(ItemPedido_.produto);
        Join<Produto, Categoria> subqueryJoinProdutoCategoria = subqueryJoinProduto
                .join(Produto_.categorias);
        subquery.select(subqueryRoot.get(ItemPedido_.id).get(ItemPedidoId_.pedidoId));
        subquery.where(criteriaBuilder.equal(subqueryJoinProdutoCategoria.get(Categoria_.id), 2));

        criteriaQuery.where(root.get(Pedido_.id).in(subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComSubqueryExercicio() {
        // todos os clientes que fizeram mais de 2 pedidos

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.count(subqueryRoot.get(Pedido_.id)));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(Pedido_.cliente), root));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, 2L));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Cliente> clientes = typedQuery.getResultList();
        assertFalse(clientes.isEmpty());

        clientes.forEach(c -> System.out.println("cliente: " + c.getNome()));
    }

    @Test
    public void perquisarComSubqueryExercicioResolvido() {
        // todos os clientes que fizeram mais de 2 pedidos - resolvido

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.count(criteriaBuilder.literal(1)));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(Pedido_.cliente), root));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, 2L));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Cliente> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }

    @Test
    public void pesquisarComExists() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(p -> System.out.println("produto: " + p.getId()));
    }

    @Test
    public void pesquisarComIN() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        Join<ItemPedido, Pedido> subqueryJoinPedido = subqueryRoot.join(ItemPedido_.pedido);
        Join<ItemPedido, Produto> subqueryJoinProduto = subqueryRoot.join(ItemPedido_.produto);

        subquery.select(subqueryJoinPedido.get(Pedido_.id));
        subquery.where(criteriaBuilder.greaterThan(
                subqueryJoinProduto.get(Produto_.preco), new BigDecimal(1000)
        ));

        criteriaQuery.where(root.get(Pedido_.id).in(subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(p -> System.out.println("Cliente: " + p.getId()));
    }

    @Test
    public void pesquisarSubqueries03() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.sum(subqueryRoot.get(Pedido_.total)));
        subquery.where(criteriaBuilder.equal(root, subqueryRoot.get(Pedido_.cliente)));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(1300)));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Cliente> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(p -> System.out.println("Cliente: " + p.getId() + " " + p.getNome()));
    }

    @Test
    public void pesquisarSubqueries02() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.avg(subqueryRoot.get(Pedido_.total)).as(BigDecimal.class));

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Pedido_.total), subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(p -> System.out.println("Produto: " + p.getId() + " " + p.getTotal()));
    }

    @Test
    public void pesquisarSubqueries01() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Produto> subqueryRoot = subquery.from(Produto.class);
        subquery.select(criteriaBuilder.max(subqueryRoot.get(Produto_.preco)));

        criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.preco), subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(p -> System.out.println("Produto: " + p.getId() + " " + p.getNome() + " " + p.getPreco()));
    }

}
