package com.nunesd66.ecommerce.jpql;

import com.nunesd66.ecommerce.EntityManagerTest;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class GroupByTest extends EntityManagerTest {

    @Test
    public void condicionarAgrupamentoComHaving() {
        String jpql = "select cat.nome, sum(ip.precoProduto) from ItemPedido ip " +
                "join ip.produto pro join pro.categorias cat " +
                "group by cat.id " +
                "having sum(ip.precoProduto) > 100";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
        lista.forEach(item -> System.out.println(item[0] + ", " + item[1]));
    }

    @Test
    public void agruparEFiltrarResultado() {

        // >> total de vendas por mes
        // "select concat(year(p.dataCriacao), '/',function('monthname', p.dataCriacao)), sum(p.total) from Pedido p " +
        //      "where year(p.dataCriacao) = year(current_date) " +
        //      "group by year(p.dataCriacao), month(p.dataCriacao)"

        // >> total de vendas por categoria
        //        "select c.nome, sum(ip.precoProduto * ip.quantidade) from ItemPedido ip " +
        //                "join ip.pedido pe " +
        //                "join ip.produto p " +
        //                "join p.categorias c " +
        //                "where year(p.dataCriacao) = year(current_date) " +
        //                "and month(p.dataCriacao) = month(current_date) " +
        //                "group by c.id"

        // >> total de vendas por cliente
        // "select c.nome, sum(i.precoProduto * i.quantidade) from Pedido p " +
        //                                "join p.cliente c " +
        //                                "join p.itens i " +
        //                "where year(p.dataCriacao) = year(current_date) " +
        //                "and month(p.dataCriacao) >= (month(current_date) -3) " +
        //                                "group by c.id "

        String jpql = "select c.nome, sum(i.precoProduto * i.quantidade) from Pedido p " +
                                "join p.cliente c " +
                                "join p.itens i " +
                "where year(p.dataCriacao) = year(current_date) " +
                "and month(p.dataCriacao) >= (month(current_date) -3) " +
                                "group by c.id ";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());

        assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println(obj[0] + " - " + obj[1]));
    }

    @Test
    public void agruparResultado() {

        // >> quantidade de produtos por categoria
        // "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id"

        // >> total de vendas por mes
        // "select concat(year(p.dataCriacao), '/',function('monthname', p.dataCriacao)), sum(p.total) from Pedido p " +
        //                "group by year(p.dataCriacao), month(p.dataCriacao)"

        // >> total de vendas por categoria
        // "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
        //                "join ip.produto p " +
        //                "join p.categorias c " +
        //                "group by c.id"

        // >> total de vendas por cliente
        // "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
        //                " join ip.pedido p join p.cliente c " +
        //                " group by c.id"

        // >> total de vendas por dia e por categoria
        // "select " +
        //      " concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), " +
        //      " concat(c.nome, ': ', sum(ip.precoProduto)) " +
        //      " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c " +
        //      " group by year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao), c.id " +
        //      " order by p.dataCriacao, c.nome "

        String jpql = "select " +
                " concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), " +
                " concat(c.nome, ': ', sum(ip.precoProduto)) " +
                " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c " +
                " group by year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao), c.id " +
                " order by p.dataCriacao, c.nome ";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());

        assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println(obj[0] + " - " + obj[1]));
    }

}
