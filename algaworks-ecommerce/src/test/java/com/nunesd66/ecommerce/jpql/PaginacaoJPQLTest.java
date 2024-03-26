package com.nunesd66.ecommerce.jpql;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Categoria;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PaginacaoJPQLTest extends EntityManagerTest {

    @Test
    public void paginarResultados() {
        String jpql = "select c from Categoria c order by c.id asc";

        TypedQuery<Categoria> typedQuery = entityManager.createQuery(jpql, Categoria.class);

        // firstResult = maxResults * (pagina -1)
        typedQuery.setFirstResult(5); // exibe a pagina 2
        typedQuery.setMaxResults(5);

        List<Categoria> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());

        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
    }

}
