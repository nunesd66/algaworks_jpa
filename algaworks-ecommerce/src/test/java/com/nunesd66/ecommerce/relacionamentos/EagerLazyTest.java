package com.nunesd66.ecommerce.relacionamentos;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;

public class EagerLazyTest extends EntityManagerTest {

    @Test
    public void verificaComportamento() {
        Pedido pedido = entityManager.find(Pedido.class, 1);
//        pedido.getItens().isEmpty();
    }

}
