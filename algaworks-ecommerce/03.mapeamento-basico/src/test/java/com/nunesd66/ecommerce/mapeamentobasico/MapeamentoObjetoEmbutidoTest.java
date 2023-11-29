package com.nunesd66.ecommerce.mapeamentobasico;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.EnderecoEntregaPedido;
import com.nunesd66.ecommerce.model.Pedido;
import com.nunesd66.ecommerce.model.StatusPedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MapeamentoObjetoEmbutidoTest extends EntityManagerTest {

    @Test
    public void analisarMapeamentoObjetoEmbutido() {
        EnderecoEntregaPedido endereco = new EnderecoEntregaPedido();
        endereco.setCep("00000-000");
        endereco.setLogradouro("Rua das Laranjeiras");
        endereco.setBairro("Centro");
        endereco.setNumero("123");
        endereco.setCidade("Uberlândia");
        endereco.setEstado("MG");

        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal(1000));
        pedido.setEnderecoEntrega(endereco);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao);
        assertNotNull(pedidoVerificacao.getEnderecoEntrega());
        assertNotNull(pedidoVerificacao.getEnderecoEntrega().getCep());
    }

}

