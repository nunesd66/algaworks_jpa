package com.nunesd66.junit;

import org.junit.jupiter.api.*;

public class EntendendoJUnitTest {

    @BeforeAll // método executada antes dos testes
    public static void iniciarTestes() {
        System.out.println("-->iniciarTestes<--");
    }

    @AfterAll // método executada depois dos testes
    public static void finalizarTestes() {
        System.out.println("--->finalizarTestes<--");
    }

    @BeforeEach // método executada antes de cada teste
    public void iniciar1Teste() {
        System.out.println(">iniciar 1 Teste");
    }

    @AfterEach // método executada depois de cada teste
    public void finalizar1Teste() {
        System.out.println(">finalizar 1 Teste");
    }

    @Test // método de teste
    public void testandoAlgo() {
        String nome = String.format("%s", "Daniel");
        Assertions.assertEquals("Daniel", nome);

        System.out.println(">testandoAlgo");
    }

    @Test // método de teste
    public void testandoOutraCoisa() {
        String str = String.format("%s", "");
        Assertions.assertTrue(str.isEmpty());

        System.out.println(">testandoOutraCoisa");
    }

}
