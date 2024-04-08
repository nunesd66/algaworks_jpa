package com.nunesd66.ecommerce.jpql;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Pedido;
import com.nunesd66.ecommerce.model.Produto;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class NamedQueryTest extends EntityManagerTest {

    @Test
    public void executarConsultaArquivoXMLEspecificoProduto() {
        TypedQuery<Produto> typedQuery = entityManager.createNamedQuery("Produto.todos", Produto.class);
        List<Produto> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void executarConsultaArquivoXMLEspecificoPedido() {
        TypedQuery<Pedido> typedQuery = entityManager.createNamedQuery("Pedido.todos", Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void executarConsulta() {
        TypedQuery<Produto> typedQuery = entityManager.createNamedQuery("Produto.listarPorCategoria", Produto.class);
        typedQuery.setParameter("categoria", 2);

        List<Produto> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
    }

}
