package com.nunesd66.ecommerce.jpql;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.enumeration.StatusPagamento;
import com.nunesd66.ecommerce.model.Pedido;
import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassandoParametrosTest extends EntityManagerTest {

    @Test
    public void passarParametro() {
//        String jpql = "select p from Pedido p join p.pagamento pag " +
//                "where p.id = ?1 and pag.status = ?2";
        String jpql = "select p from Pedido p join p.pagamento pag " +
                "where p.id = :pedidoId and pag.status = :pagamentoStatus";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
//        typedQuery.setParameter(1, 2);
//        typedQuery.setParameter(2, StatusPagamento.PROCESSANDO);
        typedQuery.setParameter("pedidoId", 2);
        typedQuery.setParameter("pagamentoStatus", StatusPagamento.PROCESSANDO);

        List<Pedido> list = typedQuery.getResultList();
        assertEquals(1, list.size());
    }

    @Test
    public void passarParametroDate() {
        String jpql = "select nf from NotaFiscal nf where nf.dataEmissao <= ?1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter(1, new Date(), TemporalType.TIMESTAMP);

        List<Pedido> list = typedQuery.getResultList();
        assertEquals(1, list.size());
    }

}
