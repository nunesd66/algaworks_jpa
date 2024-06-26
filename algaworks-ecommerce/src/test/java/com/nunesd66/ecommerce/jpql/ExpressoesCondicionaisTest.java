package com.nunesd66.ecommerce.jpql;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Cliente;
import com.nunesd66.ecommerce.model.Pedido;
import com.nunesd66.ecommerce.model.Produto;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressoesCondicionaisTest extends EntityManagerTest {

    @Test
    public void uasrExpressaoIN() {
        Cliente cliente1 = new Cliente(); // entityManager.find(Cliente.class, 1);
        cliente1.setId(1);

        Cliente cliente2 = new Cliente(); // entityManager.find(Cliente.class, 2);
        cliente2.setId(2);

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        String jpql = "select p from Pedido p where p.cliente in (:clientes)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("clientes", clientes);

        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoCase() {
//        "select p.id, " +
//                "case p.status " +
//                "   when 'PAGO' then 'Esta pago' " +
//                "   when 'CANCELADO' then 'Foi cancelado' " +
//                "   else 'Esta aguardando'" +
//                "end " +
//                "from Pedido p";

        String jpql = "select p.id, " +
                "case type(p.pagamento) " +
                "   when PagamentoBoleto then 'Pago com boleto' " +
                "   when PagamentoCartao then 'Pago com cartao' " +
                "   else 'Nao pago ainda' " +
                "end " +
                "from Pedido p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
        lista.forEach(item -> System.out.println(item[0] + ", " + item[1]));
    }

    @Test
    public void usarExpressaoDiferente() {
        String jpql = "select p from Produto p where p.id <> 1";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarBetween() {
        String jpql = "select p from Produto p " +
                "where p.preco between :precoInicial and :precoFinal";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        typedQuery.setParameter("precoInicial", new BigDecimal(499));
        typedQuery.setParameter("precoFinal", new BigDecimal(1400));

        List<Produto> lista = typedQuery.getResultList();
        assertEquals(2, lista.size());
    }

    @Test
    public void usarExpressaoCondicionalLike() {
        String jpql = "select c from Cliente c where c.nome like concat('%', :nome, '%')";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("nome", "Fernando");

        List<Object[]> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarIsNull() {
        String jpql = "select p from Produto p where p.foto is not null";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        assertTrue(lista.isEmpty());
    }

    @Test
    public void usarIsEmpty() {
        String jpql = "select p from Produto p where p.categorias is not empty";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarMaiorMenor() {
        String jpql = "select p from Produto p" +
                " where p.preco >= :precoInicial and p.preco <= :precoFinal";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        typedQuery.setParameter("precoInicial", new BigDecimal(499));
        typedQuery.setParameter("precoFinal", new BigDecimal(1400));

        List<Produto> lista = typedQuery.getResultList();
        assertEquals(2, lista.size());
    }

    @Test
    public void usarMaiorMenorData() {
        String jpql = "select p from Pedido p " +
                "where p.dataCriacao > :data";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        LocalDateTime data = LocalDateTime.now().minusDays(2);
        typedQuery.setParameter("data", data);

        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

}
