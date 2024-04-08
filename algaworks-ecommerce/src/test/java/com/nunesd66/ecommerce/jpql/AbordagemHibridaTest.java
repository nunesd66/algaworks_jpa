package com.nunesd66.ecommerce.jpql;


import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class AbordagemHibridaTest extends EntityManagerTest {

    @BeforeAll
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");

        EntityManager em = entityManagerFactory.createEntityManager();

        String jpql = "select c from Categoria c";
        TypedQuery<Categoria> typedQuery = em.createQuery(jpql, Categoria.class);

        entityManagerFactory.addNamedQuery("Categoria.listar", typedQuery);
    }

    @Test
    public void usarAbordagemHibrida(){
        TypedQuery<Categoria> typedQuery = entityManager
                .createNamedQuery("Categoria.listar", Categoria.class);

        List<Categoria> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }
}