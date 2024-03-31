package com.nunesd66.ecommerce.jpql;

import com.nunesd66.ecommerce.EntityManagerTest;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class GroupByTest extends EntityManagerTest {

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
        // "select c.nome, sum(i.precoProduto * i.quantidade) from Pedido p " +
        //                "join p.cliente c " +
        //                "join p.itens i " +
        //                "group by c.id "

        // >> total de vendas por dia e por categoria
        // "select concat(year(pe.dataCriacao), '/', month(pe.dataCriacao), '/', day(pe.dataCriacao), ' - ', c.nome), " +
        //                "count(pe) from Pedido pe " +
        //                "join pe.itens i " +
        //                "join i.produto pr " +
        //                "join pr.categorias c " +
        //                "group by c.id, year(pe.dataCriacao), month(pe.dataCriacao), day(pe.dataCriacao)"

        String jpql = "select concat(day(pe.dataCriacao), '/', month(pe.dataCriacao), '/', year(pe.dataCriacao), ' - ', c.nome), " +
                                "sum(i.precoProduto * i.quantidade) from Pedido pe " +
                                "join pe.itens i " +
                                "join i.produto pr " +
                                "join pr.categorias c " +
                                "group by c.id, year(pe.dataCriacao), month(pe.dataCriacao), day(pe.dataCriacao) " +
                "order by pe.dataCriacao, c.nome asc";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());

        assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println(obj[0] + " - " + obj[1]));
    }

}
