package com.nunesd66.ecommerce.criteria;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.dto.ProdutoDTO;
import com.nunesd66.ecommerce.model.*;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasicoCriteriaTest extends EntityManagerTest {

    @Test
    public void usarDistinct() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        root.join(Pedido_.itens);

        criteriaQuery.select(root);

        criteriaQuery.distinct(true);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> pedidos = typedQuery.getResultList();

        pedidos.forEach(p -> System.out.println("id: " + p.getId()));
    }

    @Test
    public void ordenarResultados() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Cliente_.nome)));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Cliente> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        lista.forEach(p -> System.out.println(p.getId() + " - " + p.getNome()));
    }

    @Test
    public void projetarOResultadoDTO() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProdutoDTO> criteriaQuery = criteriaBuilder.createQuery(ProdutoDTO.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(criteriaBuilder
                .construct(ProdutoDTO.class,
                        root.get("id"),
                        root.get("nome")));

        TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<ProdutoDTO> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(dto -> System.out.println(dto.getId() + " - " + dto.getNome()));
    }

    @Test
    public void projetarOResultadoTuple() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(criteriaBuilder.tuple(
                root.get("id").alias("id"),
                root.get("nome").alias("nome")));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Tuple> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(t -> System.out.println(t.get("id") + " - " + t.get("nome")));
    }

    @Test
    public void projetarOResultado() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.multiselect(root.get("id"), root.get("nome"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());

        produtos.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }

    @Test
    public void retorarTodosOsProdutosExercicio() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> produtos = typedQuery.getResultList();
        assertFalse(produtos.isEmpty());
    }

    @Test
    public void selecionarUmAtributoParaRetorno() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root.get("cliente"));

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
        Cliente cliente = typedQuery.getSingleResult();

        assertEquals("Fernando Medeiros", cliente.getNome());
    }

    @Test
    public void buscarPorIdentificador() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1L));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        Pedido pedido = typedQuery.getSingleResult();
        assertNotNull(pedido);
    }

}
