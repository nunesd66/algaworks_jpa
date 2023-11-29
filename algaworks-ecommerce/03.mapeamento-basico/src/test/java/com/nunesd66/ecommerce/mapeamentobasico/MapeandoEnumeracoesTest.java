package com.nunesd66.ecommerce.mapeamentobasico;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Cliente;
import com.nunesd66.ecommerce.model.SexoCliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MapeandoEnumeracoesTest extends EntityManagerTest {

    @Test
    public void testarEnum() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Mineiro");
        cliente.setSexo(SexoCliente.MASCULINO);

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertNotNull(clienteVerificacao);
    }
}
