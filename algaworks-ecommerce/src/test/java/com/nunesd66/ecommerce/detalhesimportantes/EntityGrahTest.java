package com.nunesd66.ecommerce.detalhesimportantes;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.base.EntidadeBaseInteger;
import com.nunesd66.ecommerce.model.Cliente;
import com.nunesd66.ecommerce.model.Cliente_;
import com.nunesd66.ecommerce.model.Pedido;
import com.nunesd66.ecommerce.model.Pedido_;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Subgraph;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EntityGrahTest extends EntityManagerTest {

    @Test
    public void buscarAtributosEssenciaisComNamedEntityGraph() {
        EntityGraph<?> entityGraph = entityManager.createEntityGraph("Pedido.dadosEssenciais");
        entityGraph.addAttributeNodes("pagamento");
//        entityGraph.addSubgraph("pagamento").addAttributeNodes("status");

        var fetchgraph = "jakarta.persistence.fetchgraph";
        var loadgraph = "jakarta.persistence.loadgraph";

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint(fetchgraph, entityGraph);
        List<Pedido> pedidos = typedQuery.getResultList();

        assertFalse(pedidos.isEmpty());
    }

    @Test
    public void buscarAtributosEssenciaisDePedido03() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes(Pedido_.dataCriacao, Pedido_.status, Pedido_.total);

        Subgraph<Cliente> subgraphCliente = entityGraph
                .addSubgraph(Pedido_.cliente);
        subgraphCliente.addAttributeNodes(Cliente_.nome, Cliente_.cpf);

        var fetchgraph = "jakarta.persistence.fetchgraph";
        var loadgraph = "jakarta.persistence.loadgraph";

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint(fetchgraph, entityGraph);
        List<Pedido> pedidos = typedQuery.getResultList();

        assertFalse(pedidos.isEmpty());
    }

    @Test
    public void buscarAtributosEssenciaisDePedido02() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes("dataCriacao", "status", "total");

        Subgraph<Cliente> subgraphCliente = entityGraph
                .addSubgraph("cliente", Cliente.class);
        subgraphCliente.addAttributeNodes("nome", "cpf");

        var fetchgraph = "jakarta.persistence.fetchgraph";
        var loadgraph = "jakarta.persistence.loadgraph";

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint(fetchgraph, entityGraph);
        List<Pedido> pedidos = typedQuery.getResultList();

        assertFalse(pedidos.isEmpty());
    }

    @Test
    public void buscarAtributosEssenciaisDePedido01() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes("dataCriacao", "status", "total");

        var fetchgraph = "jakarta.persistence.fetchgraph";
//        var loadgraph = "jakarta.persistence.loadgraph";
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(fetchgraph, entityGraph);
//        Pedido pedido = entityManager.find(Pedido.class, 1, properties);
//        assertNotNull(pedido);

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint(fetchgraph, entityGraph);
        List<Pedido> pedidos = typedQuery.getResultList();

        assertFalse(pedidos.isEmpty());
    }

}
