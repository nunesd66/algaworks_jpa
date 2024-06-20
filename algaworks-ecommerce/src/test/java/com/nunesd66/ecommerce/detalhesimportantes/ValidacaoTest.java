package com.nunesd66.ecommerce.detalhesimportantes;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Cliente;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidacaoTest extends EntityManagerTest {

    @Test
    public void validarCliente() {
        try {
            entityManager.getTransaction().begin();

            Cliente cliente = new Cliente();
            entityManager.merge(cliente);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals(e.getClass(), ConstraintViolationException.class);
        }


    }

}
