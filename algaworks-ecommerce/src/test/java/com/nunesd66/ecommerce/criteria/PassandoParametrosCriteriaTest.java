package com.nunesd66.ecommerce.criteria;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.NotaFiscal;
import com.nunesd66.ecommerce.model.Pedido;
import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PassandoParametrosCriteriaTest extends EntityManagerTest {

    @Test
    public void passarParametro() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotaFiscal> criteriaQuery = criteriaBuilder.createQuery(NotaFiscal.class);
        Root<NotaFiscal> root = criteriaQuery.from(NotaFiscal.class);

        criteriaQuery.select(root);

        ParameterExpression<Date> parameterExpressionData = criteriaBuilder
                .parameter(Date.class, "dataInicial");

        criteriaQuery.where(criteriaBuilder.equal(root.get("dataEmissao"), parameterExpressionData));

        TypedQuery<NotaFiscal> typedQuery = entityManager.createQuery(criteriaQuery);

        Calendar dataInicial = Calendar.getInstance();
        dataInicial.add(Calendar.DATE, -30);

        typedQuery.setParameter("dataInicial", dataInicial.getTime(), TemporalType.TIMESTAMP);

        List<NotaFiscal> lista = typedQuery.getResultList();
        assertNotNull(lista);
    }

}
