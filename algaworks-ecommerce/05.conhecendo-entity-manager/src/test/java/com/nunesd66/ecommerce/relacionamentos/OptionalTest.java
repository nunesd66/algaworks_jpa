package com.nunesd66.ecommerce.relacionamentos;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;

public class OptionalTest extends EntityManagerTest {

    @Test
    public void verificarComportamento() {
        Pedido pedido = entityManager.find(Pedido.class, 1);
    }

}
