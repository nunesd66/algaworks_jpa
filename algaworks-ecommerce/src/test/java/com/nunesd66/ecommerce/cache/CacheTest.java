package com.nunesd66.ecommerce.cache;

import com.nunesd66.ecommerce.model.Pedido;
import jakarta.persistence.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CacheTest {
    protected static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterAll
    public static void tearDownAfterClass() {
        entityManagerFactory.close();
    }

    private static void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {}

    }

    private static void log(String msg) {
        System.out.println(msg);
    }

    @Test
    public void ehCache() {
        Cache cache = entityManagerFactory.getCache();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        log("Buscando e incluindo no cache");
        entityManager1
                .createQuery("select p from Pedido p", Pedido.class)
                .getResultList();
        log("---");

        esperar(1);
        assertTrue(cache.contains(Pedido.class, 2));

        entityManager2.find(Pedido.class, 2);

        esperar(3);
        assertFalse(cache.contains(Pedido.class, 2));

        entityManager1.close();
        entityManager2.close();
    }

    @Test
    public void controlarCacheDinamicamente() {
        Cache cache = entityManagerFactory.getCache();

        var retrieveMode = "jakarta.persistence.cache.retrieveMode";
        var storeMode = "jakarta.persistence.cache.storeMode";

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando todos os pedidos.");
        entityManager1
                .createQuery("select p from Pedido p", Pedido.class)
//                .setHint(storeMode, CacheStoreMode.REFRESH)
                .getResultList();

        System.out.println("Buscando pedido de id 2");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        Map<String, Object> properties = new HashMap<>();
        properties.put(storeMode, CacheStoreMode.REFRESH);

        entityManager2.find(Pedido.class, 2, properties);

        assertTrue(cache.contains(Pedido.class, 2));

        entityManager1.close();
        entityManager2.close();
    }

    @Test
    public void analisarOpcoesCache() {
        Cache cache = entityManagerFactory.getCache();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1:");
        entityManager1
                .createQuery("select p from Pedido p", Pedido.class)
                .getResultList();
        assertTrue(cache.contains(Pedido.class, 1));
        assertTrue(cache.contains(Pedido.class, 2));

        entityManager1.close();
    }

    @Test
    public void verificarSeEstaNoCache() {
        Cache cache = entityManagerFactory.getCache();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1:");
        entityManager1
                .createQuery("select p from Pedido p", Pedido.class)
                .getResultList();
        assertTrue(cache.contains(Pedido.class, 1));
        assertTrue(cache.contains(Pedido.class, 2));

        entityManager1.close();
    }

    @Test
    public void removerDoCache() {
        Cache cache = entityManagerFactory.getCache();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1:");
        entityManager1
                .createQuery("select p from Pedido p", Pedido.class)
                .getResultList();

        cache.evictAll();
        cache.evict(Pedido.class);
        cache.evict(Pedido.class, 1);

        System.out.println("Buscando a partir da instância 2:");
        entityManager2.find(Pedido.class, 1);
        entityManager2.find(Pedido.class, 2);

        entityManager1.close();
        entityManager2.close();
    }

    @Test
    public void adicionarPedidosNoCache() {
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1:");
        entityManager1
                .createQuery("select p from Pedido p", Pedido.class)
                .getResultList();

        System.out.println("Buscando a partir da instância 2:");
        entityManager2.find(Pedido.class, 1);

        entityManager1.close();
        entityManager2.close();
    }

    @Test
    public void buscarDoCache() {
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1:");
        entityManager1.find(Pedido.class, 1);

        System.out.println("Buscando a partir da instância 2:");
        entityManager2.find(Pedido.class, 1);

        entityManager1.close();
        entityManager2.close();
    }

}
