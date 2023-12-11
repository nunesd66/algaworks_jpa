package com.nunesd66.ecommerce.iniciandocomjpa;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrimeiroCrudTest extends EntityManagerTest {

    @Test
    public void consultarCliente() {
        Cliente cliente = entityManager.find(Cliente.class, 1);
        assertNotNull(cliente);
    }

    @Test
    public void criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Pessoa");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Cliente clientePersistido = entityManager.find(Cliente.class, cliente.getId());
        assertEquals("João Pessoa", clientePersistido.getNome());
    }

    @Test
    public void editarCliente() {
        Cliente cliente = entityManager.find(Cliente.class, 1);
        cliente.setNome("Fernando Silva");

        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Cliente clienteMergeado = entityManager.find(Cliente.class, cliente.getId());
        assertEquals("Fernando Silva", clienteMergeado.getNome());
    }

    @Test
    public void removerCliente() {
        Cliente cliente = entityManager.find(Cliente.class, 2);

        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Cliente clienteRemovido = entityManager.find(Cliente.class, cliente.getId());
        assertNull(clienteRemovido);
    }

}
