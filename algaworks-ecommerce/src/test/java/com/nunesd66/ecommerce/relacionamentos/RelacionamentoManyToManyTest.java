package com.nunesd66.ecommerce.relacionamentos;

import com.nunesd66.ecommerce.EntityManagerTest;
import com.nunesd66.ecommerce.model.Categoria;
import com.nunesd66.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class RelacionamentoManyToManyTest extends EntityManagerTest {

    @Test
    public void verificaRelacionamento() {
        Produto produto = entityManager.find(Produto.class, 1);
        Categoria categoria = entityManager.find(Categoria.class, 1);

        entityManager.getTransaction().begin();
//        categoria.setProdutos(Collections.singletonList(produto));
        produto.setCategorias(Collections.singletonList(categoria));
        entityManager.getTransaction().commit();

        entityManager.clear();

        Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        assertFalse(categoriaVerificacao.getProdutos().isEmpty());
    }

}
