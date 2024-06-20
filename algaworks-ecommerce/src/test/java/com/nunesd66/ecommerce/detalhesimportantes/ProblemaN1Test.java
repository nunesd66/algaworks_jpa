package com.nunesd66.ecommerce.detalhesimportantes;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Pedido;
import jakarta.persistence.EntityGraph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProblemaN1Test extends EntityManagerTest {

    @Test
    public void resolverComEntityGraph() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes("cliente", "notaFiscal", "pagamento");

        var fetchgraph = "jakarta.persistence.fetchgraph";
        var loadgraph = "jakarta.persistence.loadgraph";

        List<Pedido> lista = entityManager
                .createQuery("select p from Pedido p", Pedido.class)
                .setHint(loadgraph, entityGraph)
                .getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void resolverComFetch() {
        List<Pedido> lista = entityManager
                .createQuery(
                        "select p from Pedido p " +
                        "join fetch p.cliente c " +
                        "join fetch p.pagamento pag " +
                        "join fetch p.notaFiscal nf",
                        Pedido.class)
                .getResultList();
        assertFalse(lista.isEmpty());
    }

}
