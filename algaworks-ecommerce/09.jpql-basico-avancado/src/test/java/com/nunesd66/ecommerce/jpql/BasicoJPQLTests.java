package com.nunesd66.ecommerce.jpql;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.dto.ProdutoDTO;
import com.nunesd66.ecommerce.model.Cliente;
import com.nunesd66.ecommerce.model.Pedido;
import com.nunesd66.ecommerce.model.Produto;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasicoJPQLTests extends EntityManagerTest {

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
        assertEquals(2, lista7.size());
    }

    @Test
    public void projetarNoDTO() {
        String jpql = "select new com.nunesd66.ecommerce.dto.ProdutoDTO(id, nome) from Produto";
        TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(jpql, ProdutoDTO.class);
        List<ProdutoDTO> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void projetarResultado() {
        String jpql = "select id, nome from Produto";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();
        assertTrue(lista.get(0).length == 2);
    }

    @Test
    public void selecionarUmAtributoParaRetorno() {
        String jpql = "select p.nome from Produto p";
        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
        List<String> lista = typedQuery.getResultList();
        assertTrue(String.class.equals(lista.get(0).getClass()));

        String jpqlCliente = "select p.cliente from Pedido p";
        TypedQuery<Cliente> typedQueryCliente = entityManager.createQuery(jpqlCliente, Cliente.class);
        List<Cliente> listaClientes = typedQueryCliente.getResultList();
        assertTrue(Cliente.class.equals(listaClientes.get(0).getClass()));
    }

    @Test
    public void buscarPorIdentificador() {
        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p where p.id = 1", Pedido.class);

        Pedido pedido = typedQuery.getSingleResult();
        assertNotNull(pedido);

//        List<Pedido> lista = typedQuery.getResultList();
//        assertFalse(lista.isEmpty());
    }

    @Test
    public void mostrarDiferencasQueries() {
        String jpql = "select p from Pedido p where p.id = 1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        Pedido pedido1 = typedQuery.getSingleResult();
        assertNotNull(pedido1);

        Query query = entityManager.createQuery(jpql);
        Pedido pedido2 = (Pedido) query.getSingleResult();
        assertNotNull(pedido2);

//        List<Pedido> lista = query.getResultList();
//        assertFalse(lista.isEmpty());
    }

}
