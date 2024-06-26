package com.nunesd66.ecommerce.consultasnativas;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Cliente;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StoredProceduresTest extends EntityManagerTest {

    @Test
    public void chamarNamedStoreProcedure() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createNamedStoredProcedureQuery("compraram_acima_media");

        storedProcedureQuery.setParameter("ano", 2024);

        List<Cliente> clientes = storedProcedureQuery.getResultList();

        assertFalse(clientes.isEmpty());
    }

    @Test
    public void atualizarPrecoProdutoExercicio() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("ajustar_preco_produto", Cliente.class);

        storedProcedureQuery.registerStoredProcedureParameter(
                "produto_id", Integer.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter(
                "percentual_ajuste", BigDecimal.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter(
                "preco_ajustado", BigDecimal.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("produto_id", 1);
        storedProcedureQuery.setParameter("percentual_ajuste", new BigDecimal("0.1"));

        BigDecimal precoAjustado = (BigDecimal) storedProcedureQuery
                .getOutputParameterValue("preco_ajustado");

        assertEquals(new BigDecimal("878.9"), precoAjustado);
    }

    @Test
    public void receberListaProcedure() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("compraram_acima_media", Cliente.class);

        storedProcedureQuery.registerStoredProcedureParameter(
                "ano", Integer.class, ParameterMode.IN);

        storedProcedureQuery.setParameter("ano", 2024);

        List<Cliente> clientes = storedProcedureQuery.getResultList();

        assertFalse(clientes.isEmpty());
    }

    @Test
    public void usarParametrosInEOut() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("buscar_nome_produto");

        storedProcedureQuery.registerStoredProcedureParameter(
                "produto_id", Integer.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter(
                "produto_nome", String.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("produto_id", 1);

        String nome = (String) storedProcedureQuery.getOutputParameterValue("produto_nome");

        assertEquals("Kindle", nome);
    }

}
