package com.nunesd66.ecommerce.jpql;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Produto;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DynamicQueryTest extends EntityManagerTest {

    @Test
    public void executarConsultaDinamica() {
        Produto consultado = new Produto();
        consultado.setNome("Câmera GoPro");

        List<Produto> lista = pesquisarProduto(consultado);

        assertFalse(lista.isEmpty());
        assertEquals("Câmera GoPro Hero 7", lista.get(0).getNome());
    }

    private List<Produto> pesquisarProduto(Produto consultado) {
        StringBuilder jpql = new StringBuilder("select p from Produto p where 1 = 1");

        if (consultado.getNome() != null) {
            jpql.append(" and p.nome like concat('%', :nome, '%')");
        }

        if (consultado.getDescricao() != null) {
            jpql.append(" and p.descricao like concat('%', :descricao, '%')");
        }

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql.toString(), Produto.class);

        if (consultado.getNome() != null) {
            typedQuery.setParameter("nome", consultado.getNome());
        }

        if (consultado.getDescricao() != null) {
            typedQuery.setParameter("descricao", consultado.getDescricao());
        }

        return typedQuery.getResultList();
    }

}
